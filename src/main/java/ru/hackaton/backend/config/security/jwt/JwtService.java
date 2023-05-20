package ru.hackaton.backend.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long accessTokenExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    public String generateAccessToken(UserDetails userDetails) {
        return buildToken(userDetails, accessTokenExpiration, TokenType.ACCESS_TOKEN);
    }

    public Map<String, String> generateTokens(UserDetails userDetails) {
        String accessToken = buildToken(userDetails, accessTokenExpiration, TokenType.ACCESS_TOKEN);
        String refreshToken = buildToken(userDetails, refreshTokenExpiration, TokenType.REFRESH_TOKEN);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);

        return tokens;
    }

    private String buildToken(UserDetails userDetails, long expiration, TokenType tokenType) {
        List<String> roles = null;
        if (tokenType == TokenType.ACCESS_TOKEN) {
            roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        }
        return Jwts
                .builder()
                .claim("token_type", tokenType.getName())
                .claim("roles", roles)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        final Claims claims = extractAllClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    public String extractUsername(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public String extractTokenType(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.get("token_type", String.class);
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.get("roles", List.class);
    }

    private Key getSignInKey() {
        byte[] keyBites = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBites);
    }

}