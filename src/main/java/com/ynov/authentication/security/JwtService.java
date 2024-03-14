package com.ynov.authentication.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    final static private SignatureAlgorithm alg = SignatureAlgorithm.HS256;
    final static private SecretKey SECRET_KEY = generateSecretKey();

    public String generateToken(
            UserDetails userDetails,
            Map<String, String> extraClaims
    ) {
        return Jwts
                .builder()
                .setSubject(userDetails.getUsername())
                .setClaims(extraClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(SECRET_KEY ,alg)
                .compact();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(userDetails, new HashMap<>());
    }

    private static SecretKey generateSecretKey() {
        return Keys.secretKeyFor(alg);
    }
}
