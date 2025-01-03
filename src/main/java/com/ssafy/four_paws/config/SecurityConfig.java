package com.ssafy.four_paws.config;

import com.ssafy.four_paws.security.CustomAuthenticationEntryPoint;
import com.ssafy.four_paws.security.JwtRequestFilter;
import com.ssafy.four_paws.user.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    // DaoAuthenticationProvider 빈 정의
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    // 인증 매니저 빈 정의
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 보안 필터 체인 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // CSRF 비활성화 (REST API의 경우 일반적으로 비활성화)
            .csrf().disable()
            // 세션을 사용하지 않도록 설정 (JWT는 상태를 유지하지 않음)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            // 접근 허용 설정
            .authorizeHttpRequests()
                .requestMatchers("/auth/**").permitAll() // 인증 엔드포인트는 허용
                .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
                .and()
            // 예외 처리 설정
            .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint) // 커스텀 예외 핸들러 설정
                .and()
            // 인증 제공자 설정
            .authenticationProvider(authenticationProvider())
            // 커스텀 JWT 필터 추가
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
