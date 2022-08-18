package com.bidpoint.backend.config;

import com.bidpoint.backend.item.converter.BigDecimalConverter;
import com.bidpoint.backend.item.converter.CategoryInputDtoConverter;
import com.bidpoint.backend.item.converter.ItemConverter;
import com.bidpoint.backend.item.converter.ItemInputDtoConverter;
import com.bidpoint.backend.item.repository.CategoryRepository;
import com.bidpoint.backend.user.converter.UserInputDtoConverter;
import com.bidpoint.backend.user.converter.UserConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConverterConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UserInputDtoConverter());
        registry.addConverter(new UserConverter());

        registry.addConverter(new ItemInputDtoConverter());
        registry.addConverter(new ItemConverter());

        registry.addConverter(new CategoryInputDtoConverter());

        registry.addConverter(new BigDecimalConverter());
    }
}