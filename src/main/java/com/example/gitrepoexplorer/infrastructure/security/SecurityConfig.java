package com.example.gitrepoexplorer.infrastructure.security;

import com.example.gitrepoexplorer.domain.user.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(UserRepository userRepository) {
        return new UserDetailsManagerImpl(userRepository, passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(corsConfigurerCustomizer())
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-resources/**").permitAll()
                        .requestMatchers("/users/register/**").permitAll()
                        .requestMatchers("/login/**").permitAll()
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

    private Customizer<CorsConfigurer<HttpSecurity>> corsConfigurerCustomizer() {
        return c -> {
            CorsConfigurationSource source = request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(
                        List.of("http://localhost:3000"));
                config.setAllowedMethods(
                        List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
                config.setAllowedHeaders(List.of("*"));
                return config;
            };
            c.configurationSource(source);
        };
    }
}
