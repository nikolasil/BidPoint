package com.bidpoint.backend.user.converter;

import com.bidpoint.backend.user.dto.UserOutputDto;
import com.bidpoint.backend.role.entity.Role;
import com.bidpoint.backend.user.entity.User;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class UserConverter implements Converter<User, UserOutputDto> {
    @Override
    public UserOutputDto convert(User source) {
        return new UserOutputDto(
                source.getFirstname(),
                source.getLastname(),
                source.getUsername(),
                source.getRoles().stream().map(Role::getName).collect(Collectors.toSet()),
                source.getAddress(),
                source.getPhone(),
                source.getMail(),
                source.getAfm()
                );
    }

}
