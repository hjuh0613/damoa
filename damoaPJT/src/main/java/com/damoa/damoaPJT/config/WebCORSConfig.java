package com.damoa.damoaPJT.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebCORSConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/chat/**")
                .allowedOriginPatterns("http://localhost:8080") // 허용할 출처 : 특정 도메인만 받을 수 있음
                .allowedMethods("GET", "POST")  // 허용할 HTTP 메소드
                .allowCredentials(true);    // 쿠키 인증 허용

    }

}
