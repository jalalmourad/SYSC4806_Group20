package org.sysc4806.sysc4806_group20.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.sysc4806.sysc4806_group20.Service.CustomUserDetailsService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/professor/**").hasRole("PROFESSOR")
                        .requestMatchers("/student/**").hasRole("STUDENT")
                        .requestMatchers("/login", "/registration", "/").permitAll()  // Allow these routes to be public
                        .requestMatchers("/api/userAccount/login", "/api/userAccount/validate").permitAll()  // Permit custom login API
                        .requestMatchers("/api/professors/newProfessor", "/api/students/newStudent").permitAll()
                        .anyRequest().permitAll()  // Allow all other requests without authentication
                )
                .formLogin(form -> form
                        .loginPage("/login")  // Default login page route
                        .defaultSuccessUrl("/", true)
                        .permitAll()  // Allow access to login page for everyone
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // Logout URL
                        .logoutSuccessUrl("/")  // Redirect to / after logout
                        .permitAll()  // Allow logout access for everyone
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/accessDenied")  // Page for access denied errors
                );

        // Add debugging filter to log authorities
        http.addFilterBefore(new Filter() {
            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                    throws IOException, ServletException {
                var authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null) {
                    System.out.println("Current User: " + authentication.getName());
                    System.out.println("Authorities: " + authentication.getAuthorities());
                }
                chain.doFilter(request, response);
            }
        }, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
