package com.ayno.service;

import com.ayno.config.security.CustomUserDetails;
import com.ayno.config.security.service.JwtService;
import com.ayno.dto.LoginRequestDTO;
import com.ayno.dto.LoginResponseDTO;
import com.ayno.entity.Admin;
import com.ayno.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AdminRepository adminRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public LoginResponseDTO loginAdmin(LoginRequestDTO request) {
        Admin admin = adminRepository.findById(request.getId())
                .orElseThrow(() -> new UsernameNotFoundException("관리자 계정 없음"));

        // 비밀번호 확인
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(admin.getId(), request.getPassword())
        );

        CustomUserDetails userDetails = new CustomUserDetails(
                admin.getId(), admin.getPassword(), admin.getRole()
        );

        return issueTokenResponse(userDetails);
    }

    private LoginResponseDTO issueTokenResponse(CustomUserDetails userDetails) {
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        ResponseCookie accessCookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(60 * 15)
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(60 * 60 * 24 * 7)
                .build();

        return new LoginResponseDTO(accessCookie, refreshCookie);
    }
}
