package org.choongang.admin.product.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.menus.Menu;
import org.choongang.admin.menus.MenuDetail;
import org.choongang.commons.ExceptionProcessor;
import org.choongang.commons.Utils;
import org.choongang.commons.exceptions.AlertException;
import org.choongang.product.entities.Category;
import org.choongang.product.service.CategoryInfoService;
import org.choongang.product.service.CategorySaveService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller("adminProductController")
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class ProductController implements ExceptionProcessor {

    private final CategoryValidator categoryValidator;
    private final CategorySaveService categorySaveService;
    private final CategoryInfoService categoryInfoService;

    @ModelAttribute("menuCode")
    public String getMenuCode() {
        return "product";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus() {
        return Menu.getMenus("product");
    }

    // 상품 목록
    @GetMapping
    public String list(Model model) {
        commonProcess("list", model);

        return "admin/product/list";
    }

    // 상품 등록
    @GetMapping("/add")
    public String add(Model model) {
        commonProcess("add", model);

        return "admin/product/add";
    }

    // 상품 등록, 수정 처리
    @PostMapping("/save")
    public String save(Model model) {

        return "redirect:/admin/product";
    }

    //상품 분류
    @GetMapping("/category")
    public String category(@ModelAttribute RequestCategory form, Model model) {
        commonProcess("category", model);

        List<Category> items = categoryInfoService.getList(true);
        model.addAttribute("items", items);

        return "admin/product/category";
    }


    // 상품 분류 등록
    @PostMapping("/category")
    public String categoryPs(@Valid RequestCategory form, Errors errors, Model model) {
        commonProcess("category", model);

        categoryValidator.validate(form, errors);

        if (errors.hasErrors()) {
            List<String> messages = errors.getFieldErrors()
                    .stream()
                    .map(e -> e.getCodes())
                    .map(s -> Utils.getMessage(s[0]))
                    .toList();

//            System.out.println(message);    // 오류메세지 확인용
            throw new AlertException(messages.get(0), HttpStatus.BAD_REQUEST);
        }

        categorySaveService.save(form);

        // 분류 추가가 완료되면 부모창 새로고침
        model.addAttribute("script", "parent.location.reload()");

        return "common/_execute_script";    //자바스크립트 형태로 페이지를 새로고침함
    }

    // 분류 수정
    @PatchMapping("/category")
    public String categoryEdit(@RequestParam("chk") List<Integer> chks, Model model) {
        commonProcess("category", model);


        // 수정 완료 -> 목록 갱신
        model.addAttribute("script", "parent.location.reload()");
        return "common/_execute_script";
    }

    // 카테고리 삭제
    @DeleteMapping("/category")
    public String categoryDelete(@RequestParam("chk") List<String> chks, Model model) {
        commonProcess("category", model);

        // 삭제 완료 후 -> 목록 새로고침
        model.addAttribute("script", "parent.location.reload()");
        return "common/_execute_script";
    }






    // 공통 처리 부분
    private void commonProcess(String mode, Model model) {
        mode = Objects.requireNonNullElse(mode, "list");
        String pageTitle = "상품 목록";

        List<String> addCommonScript = new ArrayList<>();
        List<String> addScript = new ArrayList<>();

        if (mode.equals("add") || mode.equals("edit")) {
            pageTitle = mode.equals("edit") ? "상품 수정" : "상품 등록";
            addCommonScript.add("ckeditor5/ckeditor");
            addCommonScript.add("fileManager");
            addScript.add("product/form");

        } else if (mode.equals("category")) {
            pageTitle = "상품 분류";
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("addScript", addScript);
        model.addAttribute("addCommonScript", addCommonScript);
        model.addAttribute("subMenuCode", mode);
    }
}
