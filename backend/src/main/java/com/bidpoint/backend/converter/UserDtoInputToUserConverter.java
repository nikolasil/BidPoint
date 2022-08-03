package com.bidpoint.backend.converter;

import com.bidpoint.backend.dto.UserDtoInput;
import com.bidpoint.backend.entity.Role;
import com.bidpoint.backend.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class UserDtoInputToUserConverter implements Converter<UserDtoInput, User> {
    @Override
    public User convert(UserDtoInput source) {
        Collection<Role> roles = source.getRoles().stream().map(r -> new Role(null,r)).collect(Collectors.toCollection(TreeSet::new));
        return new User(
                null,
                source.getFirstname(),
                source.getLastname(),
                source.getUsername(),
                source.getPassword(),
                roles);
    }

}
