package org.choongang.member.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.Utils;
import org.choongang.member.MemberUtils;
import org.choongang.member.entities.Member;
import org.choongang.member.services.JoinService;
import org.choongang.member.services.JoinService;
import org.choongang.member.services.MemberInfo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController implements ExceptionProcessor {
    private final Utils utils;
    private final JoinService joinService;

    @GetMapping("/join")
    public String join(@ModelAttribute RequestJoin form, Model model) {
        commonProcess("join", model);

        return utils.tpl("member/join");
    }

    @PostMapping("/join")
    public String joinPs(@Valid RequestJoin form, Errors errors, Model model) {
        commonProcess("join", model);

        joinService.process(form, errors);

        if (errors.hasErrors()) {
            return utils.tpl("member/join");
        }

        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login(Model model) {
        commonProcess("login", model);
        return utils.tpl("member/login");
    }

    /*
     * 회원가입 title 유지 위해
     */
    private void commonProcess(String mode, Model model) {  // 공통 처리할 메서드, 내부 사용으로 private
        mode = StringUtils.hasText(mode) ? mode : "join";
        String pageTitle = Utils.getMessage("회원가입", "commons");
        if (mode.equals("login")) {
            pageTitle = Utils.getMessage("로그인", "commons");
        }
        model.addAttribute("pageTitle", pageTitle);
    }

    /*
    @ResponseBody   // rest방식 반환
    @GetMapping("/info")
    public void info(Principal principal) {
        String username = principal.getName();
        System.out.printf("username=%s%n", username);
    }
    */
    /*
    @ResponseBody
    @GetMapping("/info")
    public void info(@AuthenticationPrincipal MemberInfo memberInfo) {

        System.out.println(memberInfo);
    }
     */
    /*
    @ResponseBody
    @GetMapping("/info")
    public void info() {
        MemberInfo memberInfo = (MemberInfo)SecurityContextHolder
                                    .getContext()
                                    .getAuthentication()
                                    .getPrincipal();
        System.out.println(memberInfo);
    }
     */
/*
    @ResponseBody
    @GetMapping("/info")
    public void info() {
        if (memberUtils.isLogin()) {
            Member member = memberUtils.getMember();
            System.out.println(member);
        } else {
            System.out.println("미로그인 상태...");
        }
    }
    */
}
