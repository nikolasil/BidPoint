package com.bidpoint.backend.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.bidpoint.backend.exception.auth.SignInException;
import com.bidpoint.backend.exception.user.UserNotApprovedException;
import com.bidpoint.backend.service.user.UserService;
import com.bidpoint.backend.util.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private final UserService userService;
    private final AuthUtil authUtil;
    private final List<String> skipAuthorization = Arrays.asList("/api/auth","/api/auth/refresh-token");

    public CustomAuthorizationFilter(UserService userService, AuthUtil authUtil) {
        this.userService = userService;
        this.authUtil = authUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (skipAuthorization.contains(request.getServletPath())) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (!authUtil.checkForAuthorizationHeader(authorizationHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            DecodedJWT decodedJWT = authUtil.decodeAuthorizationHeader(authorizationHeader);

            Collection<SimpleGrantedAuthority> authorities = authUtil.mapRolesToSimpleGrantedAuthority(decodedJWT.getClaim("roles").asArray(String.class));
            String username = decodedJWT.getSubject();

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            if(!userService.isApproved(username))
                throw new UserNotApprovedException(username);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            throw new SignInException(e);
        }
    }
}
