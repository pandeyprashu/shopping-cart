package com.ecommerce.shopping_cart.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(management->
        management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize->authorize.requestMatchers("/api/**").authenticated()
                .requestMatchers("/api/products/*/reviews").permitAll()
                .anyRequest().permitAll()
        ).addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
        .csrf(AbstractHttpConfigurer::disable)
        .cors(cors->cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

    /**
     * This method is used to configure the CORS (Cross-Origin Resource Sharing) settings.
     * It allows requests from any origin.
     *
     * @return the CorsConfigurationSource object
     */

    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(@NonNull HttpServletRequest request) {
                CorsConfiguration corsConfiguration = new CorsConfiguration();
                //URLs that should be allowed to make cross-site HTTP requests(frontend which can access backend)
                corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
                //Request methods that should be allowed(GET, POST, PUT, DELETE, OPTIONS, HEAD, TRACE)
                corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                //Request headers that should be allowed
                corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                //Whether to allow credentials
                corsConfiguration.setAllowCredentials(true);
                //Exposed headers
                corsConfiguration.setExposedHeaders(Collections.singletonList("Authorization"));
                //
                corsConfiguration.setMaxAge(3600L);
                return corsConfiguration;
            }
        };
    }

    // Password Encoder
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
