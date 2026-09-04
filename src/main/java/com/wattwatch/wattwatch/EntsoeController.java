package com.wattwatch.wattwatch;

import com.wattwatch.wattwatch.service.EntsoeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class EntsoeController {

    private final EntsoeService service;

    public EntsoeController(EntsoeService service){
        this.service = service;
    }
    @GetMapping("/api/prices/cheapest")
    public List<BigDecimal> getCheapestHours(){
        return service.theCheapest3HoursInRow(service.getDayaheadPrices());
    }

    @GetMapping("/api/prices")
    public List<BigDecimal> getAllPrices(){
        return service.getDayaheadPrices();
    }
}
