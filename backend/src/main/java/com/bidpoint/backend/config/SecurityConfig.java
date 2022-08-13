package com.bidpoint.backend.config;

import com.bidpoint.backend.auth.filter.CustomAuthenticationFilter;
import com.bidpoint.backend.auth.filter.CustomAuthorizationFilter;
import com.bidpoint.backend.user.service.UserService;
import com.bidpoint.backend.auth.service.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final ConversionService conversionService;
    private final AuthServiceImpl authServiceImpl;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(),userService,conversionService, authServiceImpl);
        customAuthenticationFilter.setFilterProcessesUrl("/api/auth");
        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/auth").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/auth/refresh-token").hasAnyAuthority("admin","seller","bidder");

        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/user").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/user", "/api/user/me").hasAnyAuthority("admin","seller","bidder");
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/user/all").hasAnyAuthority("admin");
        http.authorizeRequests().antMatchers(HttpMethod.PUT,"/api/user/approve", "/api/user/role").hasAnyAuthority("admin");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE,"/api/user/role").hasAnyAuthority("admin");

        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(userService, authServiceImpl), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
