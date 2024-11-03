package com.demo.assignment.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String JWT_SECRET = "segnewongkladng";
    private final long JWT_EXPIRATION = 604800000L;
    public String generateToken(CustomUserDetails userDetails) {
        logger.info("Generating JWT token");
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
        return Jwts.builder()
                .setSubject(Integer.toString(userDetails.getNguoiDung().getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public Integer getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return Integer.parseInt(claims.getSubject());
    }

    public boolean validateToken(String authToken){
        try{
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.info("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            logger.info("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            logger.info("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            logger.info("JWT claims string is empty");
        }
        return false;
    }
}
