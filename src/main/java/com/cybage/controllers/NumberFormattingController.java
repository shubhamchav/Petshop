package com.cybage.controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cybage.services.NumberFormattingService;

@RestController
public class NumberFormattingController {
    private final NumberFormattingService numberFormattingService;
 
    @Autowired
    public NumberFormattingController(NumberFormattingService numberFormattingService) {
        this.numberFormattingService = numberFormattingService;
    }
 
    @GetMapping("/formatNumber")
    public String formatNumber(@RequestParam("value") String value) {
        // You can specify the locale you want to use here
        Locale locale = LocaleContextHolder.getLocale();
        return numberFormattingService.formatNumber(value, locale);
    }
}
