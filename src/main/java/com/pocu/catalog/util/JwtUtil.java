package com.pocu.catalog.util;

import com.pocu.catalog.constants.SecurityConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim (String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(SecurityConstant.SECRET_KEY).parseClaimsJws(token).getBody();
    }
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    public String generateToken(UserDetails userDetails){
        // Se mai pot adauga alte chestii in jwt, nu doar username ul
        Map<String,Object> claims = new HashMap<>();
        addClaimsForToken(claims, userDetails);
        return createToken(claims, userDetails.getUsername());
    }
    private void addClaimsForToken(Map<String,Object> claims, UserDetails userDetails){
        claims.put("roles",userDetails.getAuthorities());
    }
    private String createToken(Map<String,Object> claims, String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.JWT_EXPIRATION_PERIOD)) //10 ore
                .signWith(SignatureAlgorithm.HS256, SecurityConstant.SECRET_KEY)
                .compact(); //cripteaza cu algoritmul HS256 si cheia mea "secreta"
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
