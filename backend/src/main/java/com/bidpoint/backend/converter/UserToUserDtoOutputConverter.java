package com.bidpoint.backend.converter;

import com.bidpoint.backend.dto.UserDtoOutput;
import com.bidpoint.backend.entity.Role;
import com.bidpoint.backend.entity.User;
import org.springframework.core.convert.converter.Converter;

import java.util.Collection;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class UserToUserDtoOutputConverter implements Converter<User,UserDtoOutput> {
    @Override
    public UserDtoOutput convert(User source) {
        Collection<String> roles = source.getRoles().stream().map(Role::getName).collect(Collectors.toCollection(TreeSet::new));
        return new UserDtoOutput(
                source.getFirstname(),
                source.getLastname(),
                source.getUsername(),
                roles
                );
    }

}
