package com.example.Baesh;

import com.example.Baesh.JWT.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)// CSRF 보호 비활성화 (JWT 사용 시 필요 없음)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login","/api/auth/signUp").permitAll()// 로그인 엔드포인트 공개
                        .anyRequest().authenticated())// 모든 요청은 인증 필요
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);// JWT 필터 등록

        return http.build();
    }


    /**
     * AuthenticationManager 빈 설정
     * Spring Security에서 인증을 처리하는 핵심 컴포넌트입니다.
     * AuthenticationConfiguration을 통해 기본 설정된 AuthenticationManager를 가져옵니다.
     * 이 설정이 없으면 @Autowired AuthenticationManager 주입 시 오류가 발생할 수 있습니다.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * PasswordEncoder 빈 설정
     * 비밀번호를 안전하게 해시화하기 위한 인코더를 설정합니다.
     * BCrypt 알고리즘을 사용하여 비밀번호를 해시화합니다.
     * 이 설정이 없으면 비밀번호 암호화 및 검증 시 오류가 발생할 수 있습니다.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
