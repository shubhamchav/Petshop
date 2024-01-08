package com.cybage.services;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
 
import java.text.NumberFormat;
import java.util.Locale;
 
@Service
public class NumberFormattingService {
    private final MessageSource messageSource;
 
    public NumberFormattingService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
 
    public String formatNumber(String value, Locale locale) {
        String formatPattern = messageSource.getMessage("number.format", null, locale);
 
        // Custom mapping for billion to "Md"
        if (value.endsWith("B")) {
            try {
                double number = Double.parseDouble(value.substring(0, value.length() - 1)) * 1000; // Convert billion to million
                NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
                return numberFormat.format(number) + "Md";
            } catch (NumberFormatException e) {
                // Handle the case when the input value is not a valid number
                return "Invalid Input";
            }
        }
 
        // Use NumberFormat to format regular numbers
        try {
            double number = Double.parseDouble(value);
            NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
            return numberFormat.format(number);
        } catch (NumberFormatException e) {
            // Handle the case when the input value is not a valid number
            return "Invalid Input";
        }
    }
}