package com.ayno.controller;

import com.ayno.config.security.service.CustomUserDetailsService;
import com.ayno.config.security.service.JwtService;
import com.ayno.dto.LoginRequestDTO;
import com.ayno.dto.LoginResponseDTO;
import com.ayno.dto.common.Response;
import com.ayno.service.AuthServcie;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServcie authServcie;

    @Operation(
            summary = "관리자 로그인",
            description = "아이디와 비밀번호를 입력하여 JWT를 발급받고 로그인합니다."
    )
    @PostMapping("/admin/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequestDTO request)
    {
        LoginResponseDTO response = authServcie.loginAdmin(request);
        return ResponseEntity.ok()
                .header("Set-Cookie", response.getAccessCookie().toString())
                .header("Set-Cookie", response.getRefreshCookie().toString())
                .body(Response.success("로그인 성공"));
    }
}
