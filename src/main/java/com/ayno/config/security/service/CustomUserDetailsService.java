package com.ayno.config.security.service;

import com.ayno.config.security.CustomUserDetails;
import com.ayno.entity.Admin;
import com.ayno.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
//    private final UserRepository userRepository; // 일반 유저용

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        // 관리자 조회 시도
        Optional<Admin> adminOpt = adminRepository.findById(id);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            return new CustomUserDetails(admin.getId(), admin.getPassword(), admin.getRole());
        }

        // 일반 유저 조회 시도
//        Optional<User> userOpt = userRepository.findById(id);
//        if (userOpt.isPresent()) {
//            User user = userOpt.get();
//            return new CustomUserDetails(user.getId(), user.getPassword(), user.getRole());
//        }

        // 둘 다 없으면 예외
        throw new UsernameNotFoundException("해당 ID를 가진 사용자를 찾을 수 없습니다: " + id);
    }
}



