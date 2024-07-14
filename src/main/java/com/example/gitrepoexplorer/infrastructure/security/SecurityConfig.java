package com.example.gitrepoexplorer.infrastructure.security;

import com.example.gitrepoexplorer.domain.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(UserRepository userRepository) {
        return new UserDetailsManagerImpl(userRepository, passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs").permitAll()
                        .requestMatchers("/swagger-resources/**").permitAll()
                        .requestMatchers("/users/register/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/database/repo/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/database/repos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/database/branch/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/github/repos/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/database/repo/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/database/repo/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/database/repo/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/database/repo/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/database/branch/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/database/branch/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/database/repos").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .build();
    }
}
