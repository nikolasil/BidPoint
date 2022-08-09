package com.bidpoint.backend.converter.user;

import com.bidpoint.backend.dto.user.UserOutputDto;
import com.bidpoint.backend.entity.Role;
import com.bidpoint.backend.entity.User;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserConverter implements Converter<User, UserOutputDto> {
    @Override
    public UserOutputDto convert(User source) {
        Collection<String> roles = source.getRoles().stream().map(Role::getName).collect(Collectors.toCollection(ArrayList::new));
        return new UserOutputDto(
                source.getFirstname(),
                source.getLastname(),
                source.getUsername(),
                roles
                );
    }

}
