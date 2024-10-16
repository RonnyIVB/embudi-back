package com.satgy.embudi.security;

import com.satgy.embudi.service.UserPasswordServiceImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserPasswordServiceImp userPassRepo;

    @Autowired
    private JwtTokenProvider tokenProvider;

    private String getTokenOfRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenOfRequest(request);
        if (StringUtils.hasText(token) && tokenProvider.tokenValidate(token)) {
            String userName = tokenProvider.getUserNameOfJwt(token);
            UserDetails userDetails = userPassRepo.loadUserByUsername(userName);
            List<String> userRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            userRoles.add("deleteMe"); // Only for now, because I'm not using credentials currently. Delete this line, and delete inside next if condition
            // check if the current user has any role of the DataBase (User.role - Role.roleName).
            if (userRoles.contains("User") || userRoles.contains("Admin") || userRoles.contains("deleteMe")) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}
