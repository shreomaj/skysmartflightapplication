package com.ai.skysmart.service;

import com.ai.skysmart.config.CusstomUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;


@Service
public class JwtService {
    private final String jwtSecret = "uS8d9Hs2P12A9Kd83JD92lsMmwq3L0Azx1Pl98aa4ks=";
    private final int jwtExpirationMs = 86400000; // 1 day

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username,String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getKey())
                .compact();
    }

    //    public String generateToken(CusstomUser userDetails) {
//        return Jwts.builder()
//                .setSubject(userDetails.getUsername())             // email
//                //.claim("role", userDetails.getRole().name())
//                // ADD ROLE HERE
//                .claim("role", "ROLE_" + userDetails.getRole())
//
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
//                .signWith(getKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public String extractEmail(String token){
        return  extractAllClaims(token).getSubject();
    }


    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = extractEmail(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);

    }

    private boolean isTokenExpired(String token) {

        return extractAllClaims(token).getExpiration().before(new Date());

    }

}
