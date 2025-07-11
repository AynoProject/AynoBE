package com.ayno.config.security;

import com.ayno.config.security.service.CustomUserDetailsService;
import com.ayno.config.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("[JwtFilter] 실행됨: " + request.getRequestURI());

        // 쿠키에서 accessToken 꺼내기
        String jwt = extractTokenFromCookies(request);
        String Id = null;

        // 토큰에서 Id 추출
        if (jwt != null) {
            Id = jwtService.extractId(jwt);
        }

        // 인증 안 되어있으면 인증 시도
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println("[JwtFilter] 인증 진행 시작 - SecurityContext 비어 있음");

            UserDetails userDetails = null;

            if (Id != null) {
                // accessToken이 유효하면 그대로 인증
                userDetails = userDetailsService.loadUserByUsername(Id);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities()
                            );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("[JwtFilter] accessToken 인증 성공");
                    filterChain.doFilter(request, response);
                    return;
                } else {
                    System.out.println("[JwtFilter] accessToken 유효하지 않음");
                }
            } else {
                // accessToken 없거나 무효 → refreshToken 시도
                String refreshToken = extractRefreshTokenFromCookies(request);
                if (refreshToken != null && jwtService.isRefreshTokenValid(refreshToken)) {
                    Id = jwtService.extractId(refreshToken);
                    userDetails = userDetailsService.loadUserByUsername(Id);

                    // 새 accessToken 재발급해서 쿠키에 다시 담아주기
                    String newAccessToken = jwtService.generateAccessToken(userDetails);
                    Cookie newCookie = new Cookie("accessToken", newAccessToken);
                    newCookie.setHttpOnly(true);
                    newCookie.setSecure(true);
                    newCookie.setPath("/");
                    newCookie.setMaxAge(60 * 15); // 15분
                    response.addCookie(newCookie);

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    System.out.println("[JwtFilter] refreshToken 인증 성공 → accessToken 재발급");
                } else {
                    System.out.println("[JwtFilter] refreshToken 없음 또는 유효하지 않음");
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    // 쿠키에서 accessToken 추출하는 유틸
    private String extractTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        return Arrays.stream(request.getCookies())
                .filter(cookie -> "accessToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }

    // 쿠키에서 refreshToken 추출하는 유틸
    private String extractRefreshTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) return null;
        return Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }
}