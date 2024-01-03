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
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController implements ExceptionProcessor {
    private final Utils utils;
    private final JoinService joinService;
    private final MemberUtils memberUtils;

    @GetMapping("/join")
    public String join(@ModelAttribute RequestJoin form) {

        return utils.tpl("member/join");
    }

    @PostMapping("/join")
    public String joinPs(@Valid RequestJoin form, Errors errors) {
        joinService.process(form, errors);

        if (errors.hasErrors()) {
            return utils.tpl("member/join");
        }

        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login() {

        return utils.tpl("member/login");
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
}
