package ra.project_module5_restapi_240130.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ra.project_module5_restapi_240130.security.jwt.JwtAuthTokenFilter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtAuthTokenFilter jwtAuthTokenFilter;
    //xây dựng JwtAuth trong jwt

    @Bean
    AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    // tạo bộ lọc phân quyền
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(cus -> cus.authenticationEntryPoint(authenticationEntryPoint()).accessDeniedHandler((request, response, accessDeniedException) -> {
                    log.error("errr", accessDeniedException.getMessage());
                    response.setStatus(403);
                    response.setHeader("error", "Forbiden");
                    Map<String, String> map = new HashMap<>();
                    map.put("message", "Bạn ko có quyền truy cập");
                    new ObjectMapper().writeValue(response.getOutputStream(), map);
                }))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(cus -> cus.requestMatchers("/api/v1/public/**").permitAll() // công khai
//                        .requestMatchers("/api/v1/auth/**").permitAll()
                        //public là ai cũng vào được
                                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN") //chỉ admin vào
                                .requestMatchers("/api/v1/user/**").hasAnyRole("ADMIN", "USER")
                                .anyRequest().authenticated()
                );
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
