package com.satgy.embudi.security;

import com.satgy.embudi.general.Par;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class JwtTokenProvider implements AuthenticationEntryPoint {

    /**
     * Token Generator by authentication
     * @param auth
     * @return
     */
    public String tokenGenerate(Authentication auth) {
        String userName = auth.getName();
        Date currentDate = new Date();
        Date expirationToken = new Date(currentDate.getTime() + Par.getJwtExpirationToken());//+ 300000);
        // generate token, return a String
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(currentDate)
                .setExpiration(expirationToken)
                .signWith(SignatureAlgorithm.HS512, Par.getJwtSign()).compact();
    }

    /**
     * Method to extract token
     * @param token
     * @return name or email or subject. The identity
     */
    public String getUserNameOfJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(Par.getJwtSign()).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    /**
     *
     * @param token
     * @return true is valid, false not
     */
    public Boolean tokenValidate(String token) {
        try {
            Jwts.parser().setSigningKey(Par.getJwtSign()).parseClaimsJws(token);
            return true;
        } catch(Exception e) {
            throw new AuthenticationCredentialsNotFoundException("False o expired token");
        }
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

    }
}
