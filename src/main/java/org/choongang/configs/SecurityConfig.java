package org.choongang.configs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.choongang.member.services.LoginFailureHandler;
import org.choongang.member.services.LoginSuccesHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 설정 무력화(?)

        /* 인증 설정 S - 로그인, 로그아웃 */
        http.formLogin(f -> {
            f.loginPage("/member/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(new LoginSuccesHandler())
                    .failureHandler(new LoginFailureHandler());
        });

        http.logout(c -> {
           c.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                   .logoutSuccessUrl("/member/login");
        });
        /* 인증 설정 E - 로그인, 로그아웃 */

        /* 인가 설정 S - 접근 통제 */
        // hasAuthority(..) hasAnyAuthority(...) hasRole, hasAnyRole
        // Role_롤명칭
        // hasAuthority('ADMIN')
        // ROLE_ADMIN -> hasAuthority('ROLE_ADMIN')
        // hasRole('ADMIN')
        http.authorizeHttpRequests(c -> {   // 인가 설정. 특정 권한 -> 특정 페이지 가능하도록
           c.requestMatchers("/mypage/**").authenticated()  //회원 전용
//                   .requestMatchers("/admin/**").hasAnyAuthority("ADMIN", "MANAGER")
                   .anyRequest().permitAll();   // 그 외 모든 페이지는 모두 접근 가능
        });
        http.exceptionHandling(c -> {
            c.authenticationEntryPoint((req, res, e) -> {
                String URL = req.getRequestURI();
                if (URL.indexOf("/admin") != -1) {  // 관리자 페이지
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED); // 401 권한없음
                } else {
                    res.sendRedirect(req.getContextPath() + "/member/login");
                }
            });
        });
        /* 인가 설정 E - 접근 통제 */
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
