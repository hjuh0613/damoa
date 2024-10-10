package com.damoa.damoaPJT.config;

import com.damoa.damoaPJT.user.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailService userService;

    // 시큐리티 기능 비활성화
    // Spring Security가 정적자원에 대해서 인증을 하지 않도록 설정
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers("/assets/**"); //Spring Boot는 /static/ 경로 없이 직접 /assets/**로 리소스를 참조
    }

    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/login", "/register").permitAll() // 해당 url에 대해 누구나 접근 가능하도록 설정
                                .anyRequest().authenticated() // 위에서 지정한 url 이외의 요청에 대해 인증이 성공된 상태에서만 접근 가능하도록 설정
                        )
                        // 폼 기반 로그인 설정
                        .formLogin(form -> form
                                .loginPage("/login")
                                .usernameParameter("userId")
                                .passwordParameter("userPw")
                                .defaultSuccessUrl("/"))
                        // 로그아웃 설정
                        .logout(logout -> logout
                                .logoutSuccessUrl("/login")
                                .invalidateHttpSession(true)) // 로그아웃 이후 session 전체 삭제 설정
                        .csrf(csrf -> csrf  // spring security는 기본적으로 CSRF 공격을 보호함
                                .disable()) // 비활성화 처리함
                        .build();
    }

    // 인증 관리자 관련 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http // 사용자 인증을 위해 AuthenticationProvider를 찾아 위임
            , BCryptPasswordEncoder bCryptPasswordEncoder
            , UserDetailService userDetailService) throws Exception {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService); // 실질적으로 사용자 인증 로직이 담긴 객체 설정
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);

        return new ProviderManager(authenticationProvider); // AuthenticationManager 의 구현체
    }                                                       // AuthenticationProvider 목록을 위임받음
                                                            // 위 소스에서 DaoAuthenticationProvider 부분
                                                            // authenticationProvider.setUserDetailsService(userService);
                                                            // 실질적으로 사용자 인증 로직이 담긴 객체 설정

    // pw 인코더로 사용할 bean 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
