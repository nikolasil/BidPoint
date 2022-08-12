package com.bidpoint.backend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Getter
@Component
public class AuthUtil {
    final Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
    final JWTVerifier verifier = JWT.require(this.algorithm).build();
    final Integer accessTokenExpiration = 10 * 60 * 1000;
    final Integer refreshTokenExpiration = 30 * 60 * 1000;


    public DecodedJWT decodeAuthorizationHeader(String authorizationHeader){
        String token = authorizationHeader.substring("Bearer ".length());
        return verifier.verify(token);
    }

    public Boolean checkForAuthorizationHeader(String authorizationHeader){
        return authorizationHeader != null && authorizationHeader.startsWith("Bearer ");
    }

    public Collection<SimpleGrantedAuthority> mapRolesToSimpleGrantedAuthority(String[] roles){
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        stream(roles).forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role));
        });
        return authorities;
    }

    public List<String> mapGrantedAuthorityToString(Collection<GrantedAuthority> authorities){
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    public String createAccessToken(User user, HttpServletRequest request){
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles",this.mapGrantedAuthorityToString(user.getAuthorities()))
                .sign(this.algorithm);
    }

    public String createRefreshToken(User user, HttpServletRequest request){
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
    }
}
