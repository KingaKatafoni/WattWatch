package com.wattwatch.wattwatch.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class EntsoeService {

    @Value("${entsoe.api.token}")
    private String apiToken;

    private final WebClient webClient = WebClient.create("https://web-api.tp.entsoe.eu/api");

    @Scheduled(fixedRate = 360000)
    public List<BigDecimal> getDayaheadPrices(){
        List<BigDecimal> dayPrices = List.of(
                new BigDecimal("150"),
                new BigDecimal("220"),
                new BigDecimal("-8"),
                new BigDecimal("167"),
                new BigDecimal("400"),
                new BigDecimal("146"),
                new BigDecimal("157"),
                new BigDecimal("234"),
                new BigDecimal("345"),
                new BigDecimal("365"),
                new BigDecimal("378"),
                new BigDecimal("220"),
                new BigDecimal("320"),
                new BigDecimal("178"),
                new BigDecimal("399"),
                new BigDecimal("158"),
                new BigDecimal("212"),
                new BigDecimal("245"),
                new BigDecimal("190"),
                new BigDecimal("210"),
                new BigDecimal("321"),
                new BigDecimal("1"),
                new BigDecimal("1"),
                new BigDecimal("1")

        );

        for (int i = 0; i < dayPrices.size(); i++) {
            System.out.println("Godzina " + i + ": " + dayPrices.get(i) + "PLN/MWh");
        }

        theCheapest3HoursInRow(dayPrices);
        fetchDayAheadPrices();
        return dayPrices;
    }

    public List<BigDecimal> theCheapest3HoursInRow(List<BigDecimal> allHours) {
        List<BigDecimal> cheapestHours = new ArrayList<>();
        int bestIndex = 0;
        BigDecimal bestSum = allHours.get(0).add(allHours.get(1)).add(allHours.get(2));
        BigDecimal currentSum;
        for (int i = 0; i <= allHours.size() - 3; i++) {
            currentSum = allHours.get(i).add(allHours.get(i + 1)).add(allHours.get(i + 2));
            if (currentSum.compareTo(bestSum) < 0) {
                bestSum = currentSum;
                bestIndex = i;
            }
        }
        cheapestHours.add(allHours.get(bestIndex));
        cheapestHours.add(allHours.get(bestIndex + 1));
        cheapestHours.add(allHours.get(bestIndex + 2));

        for (int i = 0; i < cheapestHours.size(); i++) {
            System.out.println("Najtansza godzina " + (bestIndex + i) + " cena: " + cheapestHours.get(i));
        }
        return cheapestHours;
    }

    public String fetchDayAheadPrices() {
        String response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("securityToken", apiToken)
                        .queryParam("documentType", "A44")
                        .queryParam("in_Domain", "10YPL-AREA-----S")
                        .queryParam("out_Domain", "10YPL-AREA-----S")
                        .queryParam("periodStart", "202609070000")
                        .queryParam("periodEnd", "202609080000")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        try {
            List<BigDecimal> prices = parsePrices(response);
            for (int i = 0; i < prices.size(); i++) {
                System.out.println("Pozycja " + (i + 1) + ": " + prices.get(i) + " EUR/MWh");
            }
        } catch (Exception ex) {
            System.out.println("Blad parsowania XML: " + ex.getMessage());
        }


        return response;
    }

    public List<BigDecimal> parsePrices(String xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(xml)));
        NodeList points = doc.getElementsByTagName("price.amount");

        List<BigDecimal> prices = new ArrayList<>();
        for (int i = 0; i < points.getLength(); i++) {
            prices.add(new BigDecimal(points.item(i).getTextContent()));
        }
        return prices;
    }
}
