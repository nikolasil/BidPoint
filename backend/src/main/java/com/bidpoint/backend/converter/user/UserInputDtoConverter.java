package com.bidpoint.backend.converter.user;

import com.bidpoint.backend.dto.user.UserInputDto;
import com.bidpoint.backend.entity.User;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;

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
                new ArrayList<>()
        );
    }

}
