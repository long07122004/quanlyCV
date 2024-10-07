package com.example.quanlycv.config;

import com.example.quanlycv.Service.CustomOAuth2UserService;
import com.example.quanlycv.Service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private AccessDeniedHandler customAccessDeniedHandler; // Thêm handler

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/some-endpoint/**") // Nếu có một số endpoint không cần CSRF bảo vệ, có thể ignore tại đây
                )
                .authorizeHttpRequests(authz -> authz

                        .requestMatchers("/login", "/forgot-password", "/register", "/oauth2/**").permitAll()
                        .requestMatchers("/tuyen-dung/**","/api/qlcv/**","/index-uv/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .failureUrl("/login?error=true") // Chuyển hướng đến trang đăng nhập nếu thất bại
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL để thực hiện logout
                        .logoutSuccessUrl("/login?logout=true") // URL để chuyển hướng sau khi logout
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(customAccessDeniedHandler) // Thêm handler vào đây
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint()
                        .oidcUserService(customOAuth2UserService)
                        .and()
                        .defaultSuccessUrl("/", true)
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}
