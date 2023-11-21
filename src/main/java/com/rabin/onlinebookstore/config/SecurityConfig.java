package com.rabin.onlinebookstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.GET,"/").hasAnyRole("USER","ADMIN")
                        .requestMatchers(HttpMethod.GET,"/addUsers/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/save/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET,"/delete/**").hasRole("ADMIN")
                        .requestMatchers("/update/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

   /* @Bean
    public UserDetailsService userDetailsService() {
        UserDetails rabin = User.builder().username("rabin")
                .password(passwordEncoder().encode("12345"))
                .roles("USER")
                .build();
        UserDetails kalyan = User.builder().username("kalyan")
                .password(passwordEncoder().encode("abcdef"))
                .roles("USER","ADMIN")
                .build();

        return new InMemoryUserDetailsManager(rabin,kalyan);
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

}