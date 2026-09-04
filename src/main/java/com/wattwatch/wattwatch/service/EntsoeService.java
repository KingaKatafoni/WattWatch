package com.wattwatch.wattwatch.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class EntsoeService {

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

        for (int i = 0; i < dayPrices.size(); i++){
            System.out.println("Godzina " + i + ": " + dayPrices.get(i) + "PLN/MWh");
        }

        theCheapest3HoursInRow(dayPrices);
        return dayPrices;
    }

    public List<BigDecimal> theCheapest3HoursInRow(List<BigDecimal> allHours){
        List<BigDecimal> cheapestHours = new ArrayList<>();
        int bestIndex = 0;
        BigDecimal bestSum = allHours.get(0).add(allHours.get(1)).add(allHours.get(2));
        BigDecimal currentSum;
        for(int i = 0; i <= allHours.size() -3; i++){
            currentSum = allHours.get(i).add(allHours.get(i+1)).add(allHours.get(i+2));
            if(currentSum.compareTo(bestSum) < 0){
                bestSum = currentSum;
                bestIndex = i;
            }
        }
        cheapestHours.add(allHours.get(bestIndex));
        cheapestHours.add(allHours.get(bestIndex + 1));
        cheapestHours.add(allHours.get(bestIndex + 2));

        for(int i = 0; i < cheapestHours.size(); i++){
            System.out.println("Najtansza godzina " + (bestIndex + i)  + " cena: " + cheapestHours.get(i));
        }
        return cheapestHours;
    }
}
