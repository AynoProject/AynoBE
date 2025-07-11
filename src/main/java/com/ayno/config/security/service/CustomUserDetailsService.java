package com.ayno.config.security.service;

import com.ayno.config.security.CustomUserDetails;
import com.ayno.entity.Admin;
import com.ayno.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String Id) throws UsernameNotFoundException {
        System.out.println("[CustomUserDetailsService] 관리자 로그인 시도: Id = " + Id);

        Admin admin = adminRepository.findById(Id)
                .orElseThrow(() -> new UsernameNotFoundException("해당 관리자를 찾을 수 없습니다: " + Id));

        return new CustomUserDetails(
                admin.getId(),
                admin.getPassword(),
                admin.getRole()
        );
    }
}



