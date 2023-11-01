//package com.ordersystemmanagement.apigateway.config;
//
//
//import com.ordersystemmanagement.apigateway.jwtservice.JwtService;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.lang.NonNull;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthFilter extends OncePerRequestFilter {
//
//    private final JwtService jwtService;
//    private final UserDetailsService userDetailsService;
//
//
//
//    @Override
//    protected void doFilterInternal(@NonNull HttpServletRequest request,
//                                    @NonNull HttpServletResponse response,
//                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
//        final String autHeader = request.getHeader("Authorization");
//        final String jwt;
//        final String userEmail;
//
//        // check if the auth header is empty or doesn't start with bearer will return and terminate the process
//    if (autHeader == null || !autHeader.startsWith("Bearer")){
//        filterChain.doFilter(request,response);
//        return;
//    }
//
//    // extract the jwt token
//    jwt = autHeader.substring(7);
//
//    userEmail = jwtService.extractUserEmail(jwt);
//
//    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
//        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
//        if (jwtService.isTokenValid(jwt, userDetails)){
//            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                    userDetails,null,userDetails.getAuthorities()
//            );
//        authToken.setDetails(
//                new WebAuthenticationDetailsSource().buildDetails(request)
//        );
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//        }
//    }
//    filterChain.doFilter(request,response);
//    }
//}
