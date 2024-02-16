package com.jun.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.jun.platform.repository")
@EntityScan("com.jun.platform.dto")
public class JunPlatformServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(JunPlatformServerApplication.class);
    }
}