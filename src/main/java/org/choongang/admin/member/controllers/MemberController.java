package org.choongang.admin.member.controllers;

import org.choongang.admin.manager.Menu;
import org.choongang.admin.manager.MenuDetail;
import org.choongang.commons.ExceptionProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("adminMemberController")
@RequestMapping("/admin/member")
public class MemberController implements ExceptionProcessor {

    @ModelAttribute("menuCode")
    public String getMenuCode() {
        return "member";
    }
    @ModelAttribute("subMenus")     // 모든 곳에서 사용할 수 있도록 attribute함
    public List<MenuDetail> getSubMenus() {
        return Menu.getMenus("member");
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("subMenuCode", "list");
        return "admin/member/list";
    }
}
