package com.bidpoint.backend.auth.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

public interface AuthService {
    DecodedJWT decodeAuthorizationHeader(String authorizationHeader);
    Boolean hasAuthorizationHeader(String authorizationHeader);
    Collection<GrantedAuthority> mapRolesToSimpleGrantedAuthority(String[] roles);
    List<String> mapGrantedAuthorityToString(Collection<GrantedAuthority> authorities);
    String createAccessToken(User user, HttpServletRequest request);
    String createAccessToken(String username, Collection<GrantedAuthority> roles, HttpServletRequest request);
    String createRefreshToken(User user, HttpServletRequest request);
    String createRefreshToken(String username, Collection<GrantedAuthority> roles, HttpServletRequest request);
}
