package com.ayno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AynoBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AynoBeApplication.class, args);
    }

}
