package com.rabin.onlinebookstore.utils;

import com.rabin.onlinebookstore.model.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

public class JwtService {

    private static String secret_key = "uhsebhufobeasifbieufbe8uifiuepwfbiuewfbuefbesfgewfewfewfewfefef";

    public static String extractToken(HttpServletRequest request) {
        try {
            return request.getHeader("Authorization");
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public boolean validateToken(String token) {
        if(token == null || isTokenExpired(token)){
            return  false;
        }else {
            return  true;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            return decodeToken(token).getExpiration().before(new Date(System.currentTimeMillis()));
        } catch (Exception ex) {
            return true;
        }
    }

    public String generateToken(Users user) {
        Key key = new SecretKeySpec(secret_key.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject("Token")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(key)
                .claim("userId", user.getUserId())
                .claim("username", user.getUsername())
                .claim("role", user.getRole())
                .compact();
    }

    public Claims decodeToken(String token) {
        try {
            Key key = new SecretKeySpec(secret_key.getBytes(), SignatureAlgorithm.HS256.getJcaName());
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (Exception ex) {
            return null;
        }
    }
}
