package com.bidpoint.backend.user.converter;

import com.bidpoint.backend.user.dto.UserInputDto;
import com.bidpoint.backend.user.entity.User;
import org.springframework.core.convert.converter.Converter;

import java.util.LinkedHashSet;

public class UserInputDtoConverter implements Converter<UserInputDto, User> {
    @Override
    public User convert(UserInputDto source) {
        return new User(
                null,
                source.getFirstname(),
                source.getLastname(),
                source.getUsername(),
                source.getPassword(),
                false,
                source.getAddress(),
                source.getPhone(),
                source.getMail(),
                source.getAfm(),
                new LinkedHashSet<>(),
                new LinkedHashSet<>(),
                new LinkedHashSet<>()
        );
    }

}
