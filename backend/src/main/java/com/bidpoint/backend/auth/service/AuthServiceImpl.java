package com.bidpoint.backend.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
    private final JWTVerifier verifier = JWT.require(this.algorithm).build();
    private final Integer accessTokenExpiration = 10 * 60 * 1000;
    private final Integer refreshTokenExpiration = 30 * 60 * 1000;

    public DecodedJWT decodeAuthorizationHeader(String authorizationHeader){
        log.info("decodeAuthorizationHeader authorizationHeader={}",authorizationHeader);
        String token = authorizationHeader.substring("Bearer ".length());
        return verifier.verify(token);
    }

    public Boolean hasAuthorizationHeader(String authorizationHeader){
        log.info("checkForAuthorizationHeader authorizationHeader={}",authorizationHeader);
        return authorizationHeader != null && authorizationHeader.startsWith("Bearer ");
    }

    public Collection<GrantedAuthority> mapRolesToSimpleGrantedAuthority(String[] roles){
        log.info("mapRolesToSimpleGrantedAuthority roles={}", (Object) roles);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role));
        });
        return authorities;
    }

    public List<String> mapGrantedAuthorityToString(Collection<GrantedAuthority> authorities){
        log.info("mapGrantedAuthorityToString authorities={}",authorities);
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    public String createAccessToken(User user, HttpServletRequest request){
        log.info("createAccessToken username={} url={}",user.getUsername(),request.getRequestURL().toString());
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", this.mapGrantedAuthorityToString(user.getAuthorities()))
                .sign(this.algorithm);
    }

    public String createAccessToken(String username, Collection<GrantedAuthority> roles, HttpServletRequest request){
        log.info("createAccessToken username={} roles ={} url={}",username,roles,request.getRequestURL().toString());
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", this.mapGrantedAuthorityToString(roles))
                .sign(this.algorithm);
    }

    public String createRefreshToken(User user, HttpServletRequest request){
        log.info("createRefreshToken username={} url={}",user.getUsername(),request.getRequestURL().toString());
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", this.mapGrantedAuthorityToString(user.getAuthorities()))
                .sign(algorithm);
    }
    public String createRefreshToken(String username, Collection<GrantedAuthority> roles, HttpServletRequest request){
        log.info("createRefreshToken username={} roles ={} url={}",username,roles,request.getRequestURL().toString());
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", this.mapGrantedAuthorityToString(roles))
                .sign(algorithm);
    }
}
