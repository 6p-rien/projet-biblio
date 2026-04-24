package fr.formation.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import fr.formation.security.jwt.JwtHeaderFilter;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http,JwtHeaderFilter jwtHeaderFilter ) throws Exception {
        http.authorizeHttpRequests(authorization -> {
            authorization.requestMatchers("/api/auth", "/api/inscription").permitAll();
            authorization.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll();
            authorization.requestMatchers(HttpMethod.GET, "/**").hasAnyRole("USER", "ADMIN");
            authorization.requestMatchers(HttpMethod.POST, "/api/avis").hasRole("USER");
            authorization.requestMatchers(HttpMethod.POST, "/**").hasRole("ADMIN");
        });

        http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"));
        http.addFilterBefore(jwtHeaderFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:4201"));

        corsSource.registerCorsConfiguration("/**", corsConfiguration);

        return corsSource;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
