package org.top.animalshelter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.top.animalshelter.security.DbUserDetailsService;
import org.top.animalshelter.user.UserRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private  final UserRepository userRepository;
    private final DataSource dataSource;

    public SecurityConfiguration(UserRepository userRepository, DataSource dataSource) {
        this.userRepository = userRepository;
        this.dataSource = dataSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        // настройка конфига защиты
        // запросы, которые нам необходимо будет защищать, либо разрешать
        http.authorizeHttpRequests(r ->
                        // будем требовать аутентификацию
                        // разрешим доступ ко всем либам "webjars" (например, к 'bootstrap')
                        // здесь доступ разрешен всем авторизованным пользователям
                        r.requestMatchers("/", "webjars/**").permitAll()
                                .requestMatchers("/animals/**", "/cities/**",
                                        "/types/**", "/users/**", "/guardians/**").authenticated()

                                // здесь задаем еще и метод
                                // .requestMatchers(HttpMethod.GET, "/myCard/**", "webjars/**").authenticated()

                                // здесь доступ только для админов
                                //.requestMatchers("/my_card").hasRole("ADMIN")
                                // значит в 'GrantedAuthority' должно быть указано ROLE_ADMIN

                                // всему остальному мы будем разрешать доступ
                                .anyRequest().permitAll()
                // разрешать зайти на форму логина, форма логина доступна всем
        ).formLogin(form -> form.loginPage("/login").permitAll().defaultSuccessUrl("/"))
                .csrf(AbstractHttpConfigurer::disable);;

        // сборка конфига защиты
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Сервисы для внедрения компонентов БД-сервисов в использование 'Spring Security'
    @Bean
    public UserDetailsService userDetailsService() {
        return new DbUserDetailsService(userRepository);
    }

    // Зависимости, необходимые для работы Spring Security
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // это по сути UserDetailsService
    @Bean
    public UserDetailsManager users(HttpSecurity http) throws Exception {
        // Создаем AuthenticationManagerBuilder без использования deprecated .and()
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());

        // Добавляем authentication provider отдельно
        authBuilder.authenticationProvider(daoAuthenticationProvider());

        AuthenticationManager authenticationManager = authBuilder.build();
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setAuthenticationManager(authenticationManager);
        return jdbcUserDetailsManager;
    }
}