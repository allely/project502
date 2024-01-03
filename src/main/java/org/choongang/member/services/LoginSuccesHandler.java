package org.choongang.member.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.choongang.member.MemberUtils;
import org.choongang.member.entities.Member;
import org.codehaus.groovy.util.StringUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class LoginSuccesHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        MemberUtils.clearLoginData(session);

        /* 회원 정보 조회 편의 구현 */
        MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();
        Member member = memberInfo.getMember();


        String redirectURL = request.getParameter("redirectURL");
        redirectURL = StringUtils.hasText(redirectURL) ? redirectURL : "/";

        response.sendRedirect(request.getContextPath() + redirectURL);
    }
}
