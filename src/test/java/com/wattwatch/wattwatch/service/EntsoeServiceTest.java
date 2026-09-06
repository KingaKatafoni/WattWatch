package com.wattwatch.wattwatch.service;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntsoeServiceTest {


    @Test
    void shouldFindCheapest3HoursInRow(){
        List<BigDecimal> prices = List.of(
                new BigDecimal("100"),
                new BigDecimal("50"),
                new BigDecimal("10"),
                new BigDecimal("200"),
                new BigDecimal("30")
        );

        EntsoeService service = new EntsoeService();
        List<BigDecimal> results = service.theCheapest3HoursInRow(prices);

        assertEquals(List.of(
                new BigDecimal("100"),
                new BigDecimal("50"),
                new BigDecimal("10")
        ), results);
    }

    @Test
    void shouldFindCheapest3HoursInRowWhenNegatives(){
        List<BigDecimal> prices = List.of(
                new BigDecimal("200"),
                new BigDecimal("-5"),
                new BigDecimal("-10"),
                new BigDecimal("100"),
                new BigDecimal("50")
        );

        EntsoeService service = new EntsoeService();
        List<BigDecimal> results = service.theCheapest3HoursInRow(prices);

        assertEquals(List.of(
                new BigDecimal("-5"),
                new BigDecimal("-10"),
                new BigDecimal("100")
        ), results);
    }

    @Test
    void shouldFindCheapest3HoursInRowWhenCaseAtTheEnd(){
        List<BigDecimal> prices = List.of(
                new BigDecimal("300"),
                new BigDecimal("400"),
                new BigDecimal("500"),
                new BigDecimal("10"),
                new BigDecimal("20"),
                new BigDecimal("30")
        );

        EntsoeService service = new EntsoeService();
        List<BigDecimal> results = service.theCheapest3HoursInRow(prices);

        assertEquals(List.of(
                new BigDecimal("10"),
                new BigDecimal("20"),
                new BigDecimal("30")
        ), results);
    }

    @Test
    void shouldFindCheapest3HoursInRowWhenAllTheSame(){
        List<BigDecimal> prices = List.of(
                new BigDecimal("100"),
                new BigDecimal("100"),
                new BigDecimal("100"),
                new BigDecimal("100"),
                new BigDecimal("100")
        );

        EntsoeService service = new EntsoeService();
        List<BigDecimal> results = service.theCheapest3HoursInRow(prices);

        assertEquals(List.of(
                new BigDecimal("100"),
                new BigDecimal("100"),
                new BigDecimal("100")
        ), results);

    }

}