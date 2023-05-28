package by.academy.config;

import by.academy.handlers.CustomAuthenticationSuccessHandler;
import by.academy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static by.academy.util.Constants.*;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .anyRequest().permitAll()
                .and()
                .formLogin(login -> login
                        .loginPage(LOGIN)
                        .defaultSuccessUrl(DEFAULT_SUCCESS_URL)
                        .permitAll())
                .oauth2Login(config -> config
                        .loginPage(LOGIN)
                        .defaultSuccessUrl(DEFAULT_SUCCESS_URL)
                        .successHandler(new CustomAuthenticationSuccessHandler(userService)))
                .logout(logout -> logout
                        .logoutUrl(LOGOUT)
                        .logoutSuccessUrl(LOGIN))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .invalidSessionUrl(LOGIN));
        return http.build();
    }
}
