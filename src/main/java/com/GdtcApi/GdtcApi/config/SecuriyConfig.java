package com.GdtcApi.GdtcApi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

@Configuration
@EnableWebSecurity // now it wont use default security
public class SecuriyConfig {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(customizer -> customizer.disable()) //with this we wont need to give csrf header for post put delete
                .authorizeHttpRequests(request -> request
                                                                                    .requestMatchers("register","login") //in this if the post url has register ot login it wont ask for the authentication
                                                                                    .permitAll() //other then this all need to be authenticated
                                                                                    .requestMatchers(
                                                                                            "/v3/api-docs/**",
                                                                                            "/swagger-ui/**",
                                                                                            "/swagger-ui.html"
                                                                                    )
                                                                                    .permitAll()
                                                                                    .anyRequest().authenticated()) //with this no one should be abe=le to access without being authenticated
//                .formLogin(Customizer.withDefaults()) // it will provide default login page in web **if we are using stateless we have to comment this
//                .httpBasic(Customizer.withDefaults()) // with this you will be able to use in postman because of this we getting username and password also
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //this will create session each time you request something

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .securityContext(securityContext -> securityContext.requireExplicitSave(false)) // ensures that spring security retains the authentication context across async execution

                /*
            Prevents Cross Site Scripting - Injection of malicious scripts into web pages viewed by user
            By implementing the below code it will remove any Xss content from the header if detected
         */
            .headers(headers ->
                headers.xssProtection(
                        //Enables the XSS protection in the browser
                        //And Instruct to block the page if xss content is detected
                        xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
                ).contentSecurityPolicy(
                        //Allows script to be added only from the same origin
                        //Helps to prevent Xss attack by disallowing the External scripts
                        cps -> cps.policyDirectives("script-src 'self'")
                ))
                .build();
    }



//    To Create a default username and password with this now in property it wont work
//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        UserDetails user1 = User                      // we are using userDetails because it is extending userDetails Service
//                .withDefaultPasswordEncoder()
//                .username("user1")
//                .password("user1")
//                .roles("USER")
//                .build();
//
//        UserDetails user2 = User
//                .withDefaultPasswordEncoder()
//                .username("user2")
//                .password("user2")
//                .roles("ADMIN")
//                .build();
//
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }

    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider   provider = new DaoAuthenticationProvider(); //we are using DaoAuthenticationProvider because it is implementing authenticationProvider()
//        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance()); // with this it will just take the normal password without bcrypt
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12)); // now this will convert normal password in to bcrypt so we can enter normal pass
        provider.setUserDetailsService(userDetailsService); // now the username and pass we can store in databasewe have tp create service which implements userDetailService
        return provider;
    }

    // before AuthenticationProvider was using default authentication manager and was doing every thing on his own now we are using our own
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception //we used AuthenticationConfiguration because it has authentication manager inside
    {
        return config.getAuthenticationManager();
    }
}
