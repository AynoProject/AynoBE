package com.ayno.config.security;

//INSERT INTO ADMIN (ID, PASSWORD, ROLE)
//VALUES ('testuser', '$2a$10$RzZIfRSrHTe4/ia9C72JReBB42mNEqE5jFPfbZJzfUMphE41z6eWy', 'ADMIN');

public class BcryptGenerator {
    public static void main(String[] args) {
        System.out.println(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("testpass"));
    }
}

