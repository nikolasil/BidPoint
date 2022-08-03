package com.bidpoint.backend.config;

import com.bidpoint.backend.converter.UserDtoInputToUserConverter;
import com.bidpoint.backend.converter.UserToUserDtoOutputConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConverterConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UserDtoInputToUserConverter());
        registry.addConverter(new UserToUserDtoOutputConverter());
    }
}