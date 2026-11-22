package com.example.flowboard.service;

import com.example.flowboard.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.io.Decoders;

import javax.crypto.SecretKey;
import java.util.Date;


@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long exprirationTime;

    @Value("${security.jwt.issuer}")
    private String issuer;

    public String createJwtToken(User user) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder().subject(user.getEmail()).issuedAt(new Date(System.currentTimeMillis()))
                .issuer(issuer).expiration(new Date(System.currentTimeMillis() + exprirationTime))
                .signWith(key).compact();
    }

    public Claims getTokenClaims(String token){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        try{
            Claims claims = Jwts.parser().verifyWith(key).build()
                    .parseSignedClaims(token).getPayload();

            Date expDate = claims.getExpiration();
            Date currentDate = new Date();
            if(currentDate.before(expDate)){
                return claims;
            }
        }
        catch (Exception e){}
        return null;
    }
}
