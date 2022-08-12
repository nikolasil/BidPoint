package com.bidpoint.backend.config;

import com.bidpoint.backend.filter.CustomAuthenticationFilter;
import com.bidpoint.backend.filter.CustomAuthorizationFilter;
import com.bidpoint.backend.service.user.UserService;
import com.bidpoint.backend.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
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
    private final AuthUtil authUtil;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(),userService,conversionService,authUtil);
        customAuthenticationFilter.setFilterProcessesUrl("/api/auth");
        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers( "/api/auth/**").permitAll();
        http.authorizeRequests().antMatchers( "/api/user").permitAll();
        http.authorizeRequests().antMatchers( "/api/user/me").hasAnyAuthority("admin","seller","visitor","bidder");
        http.authorizeRequests().antMatchers( "/api/user/**").hasAnyAuthority("admin");
        http.authorizeRequests().antMatchers( "/api/user/role").hasAnyAuthority("admin");
        http.authorizeRequests().antMatchers( "/api/item").permitAll();

        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(userService,authUtil), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
