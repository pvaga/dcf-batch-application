/*
 * Created on Aug 15, 2025
 *
 * Department of Social Services, Massachusetts.
 * This file is part of FamilyNet Application.
 *
 * $Header$
 */
package gov.mass.dcf.batch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Security configuration for the DCF Batch Application.
 *
 * Restricts access to /swagger-ui/** endpoints to ADMIN users only.
 * Admin username and password are loaded from application properties.
 *
 * @author prava
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    /**
     * Configures HTTP security for the application.
     *
     * Restricts /swagger-ui/** to users with ADMIN role and permits all other requests.
     * Enables HTTP Basic authentication and disables CSRF.
     *
     * @param http the HttpSecurity to modify
     * @return the SecurityFilterChain
     * @throws Exception if an error occurs
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> authz.requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).hasRole("ADMIN")
                    							 .anyRequest().permitAll()
					             )
					             .httpBasic();
        
    	http.csrf().disable();
    	
        return http.build();
    }

    /**
     * Configures the in-memory user details service with admin credentials.
     *
     * @param passwordEncoder the password encoder bean
     * @return UserDetailsService with admin user
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.builder()
            .username(this.adminUsername)
            .password(passwordEncoder.encode(this.adminPassword))
            .roles("ADMIN")
            .build();
        
        return new InMemoryUserDetailsManager(admin);
    }

    /**
     * Provides a BCrypt password encoder bean.
     *
     * @return PasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
}
