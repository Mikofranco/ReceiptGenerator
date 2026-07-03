package com.example.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean; 
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://192.168.x.x:19000", // Expo
                "exp://192.168.x.x:19000",
                "http://localhost:8081",
                "*"
        ));

        configuration.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "DELETE", "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of("*"));

        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Explicitly permit auth endpoints
                        .requestMatchers("/api/auth/login", "/api/auth/register").permitAll()

                        // You can add more public endpoints here
//                         .requestMatchers("/public/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // Secure all other endpoints
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                                .authenticationEntryPoint((request, response, authException) -> {
                                    response.setContentType("application/json");
                                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                                    response.getWriter().write("""
                                {
                                  "success": false,
                                  "error": "Unauthorized",
                                  "message": "Please login to continue",
                                  "timestamp": "%s"
                                }
                                """.formatted(java.time.LocalDateTime.now()));
                                })

                                // When user is logged in but doesn't have permission
                                .accessDeniedHandler((request, response, accessDeniedException) -> {
                                    response.setContentType("application/json");
                                    response.setStatus(HttpStatus.FORBIDDEN.value());
                                    response.getWriter().write("""
                                {
                                  "success": false,
                                  "error": "Forbidden",
                                  "message": "You don't have permission to access this resource",
                                  "timestamp": "%s"
                                }
                                """.formatted(java.time.LocalDateTime.now()));
                                })
                        );

        return http.build();
    }
}