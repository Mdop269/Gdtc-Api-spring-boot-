package com.GdtcApi.GdtcApi.config;

import com.GdtcApi.GdtcApi.Service.SecurityService.JWTService;
import com.GdtcApi.GdtcApi.Service.SecurityService.MyUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter  extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    // we obtain our MyuserService bean from the spring context
    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        System.out.println("Authorization "+ authHeader); // for debug
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            // Load User Details and validate  token
            UserDetails userDetails = context.getBean(MyUserService.class).loadUserByUsername(username);
            if(jwtService.validateToken(token, userDetails)){
                // If valid, set authentication in the security context Create authtoken
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

                // Set tenant context so that our multi-tenant connection provider can use it
                String tenantId = sanitizeDatabaseName(username);
                TenantContext.setCurrentTenant(tenantId);

            }

        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            // clearing the tenant Context after the request
            TenantContext.clear();
        }

    }

    //Helper to sanitize username into valid db name
    private String sanitizeDatabaseName(String username) {
        return username.replaceAll("[^a-zA-Z0-9_]", "_").toLowerCase();
    }

}
