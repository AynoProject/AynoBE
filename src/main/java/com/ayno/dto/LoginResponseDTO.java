package com.ayno.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseCookie;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    private transient ResponseCookie accessCookie;  // Set-Cookie용
    private transient ResponseCookie refreshCookie; // Set-Cookie용
}
