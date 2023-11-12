package com.ordersystemmanagement.apigateway.jwtservice;

import com.ordersystemmanagement.apigateway.entity.User;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;




@Service
// TODO: 11/12/2023 why is it here and not in use-management
public class JwtService {


    private static final String SECRET_KEY = "1E66D4D5D79AB473DD4FE24221AF6596EC9E7D177C52AC25346DB4F88C";


    public String extractUserEmail(String jwtToken){
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public <T> T extractClaim(String jwtToken, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }
    public String generateToken(User userDetails){
        return generateToken(new HashMap<>(), userDetails) ;

    }

    public String generateToken(Map<String, Object> extraClaims, User userDetails){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public boolean isTokenValid(String jwtToken, User userDetails){
        final String email = extractUserEmail(jwtToken);
        return (email.equals(userDetails.getEmail())) && !isTokenExpired(jwtToken);
    }

    public boolean isTokenValid(String jwtToken){

        return !isTokenExpired(jwtToken);
    }

    public boolean isTokenExpired(String jwtToken) {
        return extractExpirationDate(jwtToken).before(new Date());
    }

    public Date extractExpirationDate(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    public Claims extractAllClaims(String jwtToken){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
