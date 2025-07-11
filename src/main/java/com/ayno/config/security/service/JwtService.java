package com.ayno.config.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secretKey;

    private final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15; // 15분
    private final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7일

    // 토큰 생성
    private String generateToken(String userId, long expiration) {
        return Jwts.builder()
                .setSubject(userId) // 식별자
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Access 토큰 생성
    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(userDetails.getUsername(), ACCESS_TOKEN_EXPIRATION);
    }

    // Refresh 토큰 생성
    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(userDetails.getUsername(), REFRESH_TOKEN_EXPIRATION);
    }


    ////////////////////////////////////////////////////////////

    // 토큰 유효성 검증 => 진짜 인증용
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String Id = extractId(token);
        return (Id.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // 리프레시 토큰 유효성 검증 => 재발급용
    public boolean isRefreshTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token); // 시크릿 키로 파싱
            Date expiration = claims.getExpiration();
            return !expiration.before(new Date()); // 만료 안 됐으면 true
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰에서 Id 꺼내기
    public String extractId(String token) {
        return extractAllClaims(token).getSubject();
    }

    // 토큰 만료 검사 => 토큰에서 만료시간 뽑아서 현시간과 비교후 더 전이면 true(만료되었다)
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // JWT에서 서명키로 접속해서 Claims(내용 전체)를 추출
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 서명키 디코딩 및 HMAC SHA 알고리즘에 맞는 Key 객체 반환
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
