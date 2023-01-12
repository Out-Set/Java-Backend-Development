package com.example.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private Utils jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Fetching the header and checking the substring whether token is present or not.
        final String authorizationHeader = request.getHeader("Authorization"); // Bearer <token_value>

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            // Getting token from the header
            jwt = authorizationHeader.substring(7);

            // Now, extracting user-name from this token by calling the function extractUsername and passing jwt.
            username = jwtUtil.extractUsername(jwt);
        }

        // ghwhqjbdjk -> sachin
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // After extracting the username using jwt token from the above if-block.
            // If username is not null, also found that it(user) is not present context.
            // From the below line we will verify the user from database, that's why here is a database(DB) call.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Imp: Whenever you make the call to DB, ensure to set the context so that next time you won't have to enter into the below if-block.

            if (jwtUtil.validateToken(jwt, userDetails)) {

                // Below 3-lines are just creating an authentication object and setting the context to that authentication object.
                // Goto the docs.spring.io for detailed instruction.
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        // If user-name is null, do nothing just pass the request to the next filter.
        chain.doFilter(request, response);
    }

}
