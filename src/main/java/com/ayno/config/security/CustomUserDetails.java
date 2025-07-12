package com.ayno.config.security;

import com.ayno.entity.enums.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final String Id;
    private final String password;
    private final RoleType role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // ROLE_ prefix 붙여야 Spring Security가 인식함
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return Id;
    }

    public RoleType getRole() {
        return this.role;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // 계정 만료 여부 (필요 시 DB 필드 추가해서 관리)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // 계정 잠금 여부
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // 비밀번호 만료 여부
    }

    @Override
    public boolean isEnabled() {
        return true;  // 계정 활성화 여부
    }
}