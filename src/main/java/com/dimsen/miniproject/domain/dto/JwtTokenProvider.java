package com.dimsen.miniproject.domain.dto;

import com.dimsen.miniproject.domain.dao.UserDao;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private Long expiration = 1000L * 60 * 60;

    public String generateToken(Authentication authentication) {
        final UserDao userDao = (UserDao) authentication.getPrincipal();

        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + expiration);

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDao.getUsername());
        log.info("Claims generateToken: {}", claims);
        log.info("now getUsername: {}", now);
        log.info("expiryDate getUsername: {}", expiryDate);

        return Jwts.builder()
                .setId(userDao.getId().toString())
                .setSubject(userDao.getUsername())
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Expired Jwt Token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported Jwt Token: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid Jwt Token: {}", e.getMessage());
        } catch (SignatureException e) {
            log.error("Invalid Jwt Signature: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Jwt Claim is empty: {}", e.getMessage());
        }
        return false;
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        log.info("Claims getUsername: {}", claims);
        return claims.get("username").toString();
    }
}
