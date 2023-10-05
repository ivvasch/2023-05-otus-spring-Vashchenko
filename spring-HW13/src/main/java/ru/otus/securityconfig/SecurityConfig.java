package ru.otus.securityconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.otus.service.UserService;

@Configuration
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    private final static String ADMIN = "ADMIN";
    private final static String USER = "USER";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/images/**").permitAll()
                        .requestMatchers("/bookpage/*").hasAnyRole(ADMIN, USER)
                        .requestMatchers("/editbook/*").hasRole(ADMIN)
                        .requestMatchers("/add").hasRole(ADMIN)
                        .requestMatchers(HttpMethod.POST, "/update").hasRole(ADMIN)
                        .requestMatchers("/addcomment/*").hasAnyRole(ADMIN, USER)
                        .requestMatchers("/deletecomment/*/*").hasRole(ADMIN)
                        .requestMatchers("deletebook/*").hasRole(ADMIN)
                        .requestMatchers("/comment").hasAnyRole(ADMIN, USER)
                        .requestMatchers("/library").hasAnyRole(ADMIN, USER)
                )
                .formLogin(Customizer.withDefaults())
                .userDetailsService(userService);
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
