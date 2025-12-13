package org.top.animalshelter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        // настройка конфига защиты
        // запросы, которые нам необходимо будет защищать, либо разрешать
        http.authorizeHttpRequests(r ->
                        // будем требовать аутентификацию
                        // разрешим доступ ко всем либам "webjars" (например, к 'bootstrap')
                        // здесь доступ разрешен всем авторизованным пользователям
                        r.requestMatchers("/page/**", "/animals/**", "/cities/**",
                                        "/types/**", "/users/**", "/guardians/**").hasRole("ADMIN")

                                // здесь задаем еще и метод
                                // .requestMatchers(HttpMethod.GET, "/myCard/**", "webjars/**").authenticated()

                                // здесь доступ только для админов
                                //.requestMatchers("/my_card").hasRole("ADMIN")
                                // значит в 'GrantedAuthority' должно быть указано ROLE_ADMIN

                                // всему остальному мы будем разрешать доступ
                                .anyRequest().permitAll()
                // разрешать зайти на форму логина, форма логина доступна всем
        ).formLogin(form -> form.permitAll().successForwardUrl("/index"));

        // сборка конфига защиты
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password("qwerty")
                .passwordEncoder(passwordEncoder()::encode)
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("qwerty")
                .passwordEncoder(passwordEncoder()::encode)
                .roles("ADMIN")
                .build();
                return new InMemoryUserDetailsManager(user, admin);
    }
}