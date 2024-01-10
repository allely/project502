package org.choongang.configs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.choongang.member.services.LoginFailureHandler;
import org.choongang.member.services.LoginSuccesHandler;
import org.choongang.member.services.MemberInfoService;
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
@RequiredArgsConstructor
public class SecurityConfig {


    private final MemberInfoService memberInfoService;

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

        // 같은 출처(서버)의 사이트에서는 iframe 사용가능하도록 설정
        http.headers(c -> c.frameOptions(f -> f.sameOrigin()));

        /* 자동 로그인 설정 S */
        http.rememberMe(c -> {
            c.rememberMeParameter("autoLogin") // 자동 로그인으로 사용할 요청 파리미터 명, 기본값은 remember-me
                    .tokenValiditySeconds(60 * 60 * 24 * 30) // 로그인을 유지할 기간(30일로 설정), 기본값은 14일
                    .userDetailsService(memberInfoService) // 재로그인을 하기 위해서 인증을 위한 필요 UserDetailsService 구현 객체
                    .authenticationSuccessHandler(new LoginSuccesHandler()); // 자동 로그인 성공시 처리 Handler

        });
        /* 자동 로그인 설정 E */



        return http.build();
    }




    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
