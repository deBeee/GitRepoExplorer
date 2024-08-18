package com.example.gitrepoexplorer.infrastructure.security;

import com.example.gitrepoexplorer.domain.user.UserRepository;
import com.example.gitrepoexplorer.infrastructure.security.jwt.CustomOidcUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private static final String ADMIN_ROLE = "ADMIN";

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
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationSuccessHandler successHandler, CustomOidcUserService customOidcUserService) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(corsConfigurerCustomizer())
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .oauth2Login(c -> c.successHandler(successHandler)
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(customOidcUserService)))
//                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-resources/**").permitAll()
                        .requestMatchers("/users/register/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/database/repo/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/database/repos").authenticated() //testing purposes
                        .requestMatchers(HttpMethod.GET, "/message").hasRole(ADMIN_ROLE) //testing purposes
                        .requestMatchers(HttpMethod.GET, "/database/branch/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/github/repos/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/database/repo/**").hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.PUT, "/database/repo/**").hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.PATCH, "/database/repo/**").hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.DELETE, "/database/repo/**").hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.PATCH, "/database/branch/**").hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.DELETE, "/database/branch/**").hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.DELETE, "/database/repos").hasRole(ADMIN_ROLE)
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