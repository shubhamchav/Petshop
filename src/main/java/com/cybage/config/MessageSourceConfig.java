package com.cybage.config;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

 
@Configuration
public class MessageSourceConfig {
 
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        
        // Set the base name for your properties files (without the .properties extension)
        messageSource.setBasename("messages");
        
        // Set the default encoding for your properties files (usually UTF-8)
        messageSource.setDefaultEncoding("UTF-8");
        
        return messageSource;
    }
}