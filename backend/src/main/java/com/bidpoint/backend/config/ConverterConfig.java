package com.bidpoint.backend.config;

import com.bidpoint.backend.chat.converter.MessageConverter;
import com.bidpoint.backend.item.converter.*;
import com.bidpoint.backend.item.converter.xml.ItemToItemXmlConverter;
import com.bidpoint.backend.item.converter.xml.BidXmlToBidConverter;
import com.bidpoint.backend.item.converter.xml.ItemXmlToItemConverter;
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
        registry.addConverter(new ItemToItemXmlConverter());
        registry.addConverter(new ItemXmlToItemConverter());
        registry.addConverter(new BidXmlToBidConverter());

        registry.addConverter(new StringToCategoryConverter());

        registry.addConverter(new BigDecimalConverter());

        registry.addConverter(new BidConverter());

        registry.addConverter(new MessageConverter());
    }
}