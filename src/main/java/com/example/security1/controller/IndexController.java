package com.example.security1.controller;

import com.example.security1.model.User;
import com.example.security1.repository.UserRepository;
import com.example.security1.service.IndexService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    @Autowired
    private IndexService service;

    @GetMapping({"", "/"})
    public String index() {
        // 머스테치 기본폴더 src/main/resources/
        return "index";
    }

    @GetMapping("/user")
    @ResponseBody
    public String user(Authentication authentication, Model model) {
        UserDetails ud = (UserDetails) authentication.getPrincipal();
        return ud.getUsername();
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    @ResponseBody
    public String manager() {
        return "manager";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    /**
     * 회원가입 메소드
     * @param username username
     * @param password pawword
     * @param email email
     * @return String
     */
    @PostMapping("/join")
    public String join(
            @Param("username") String username,
            @Param("password") String password,
            @Param("email") String email
    ) {
        service.join(username, password, email);
        return "redirect:/loginForm";
    }

    @GetMapping("/info")
    @Secured("ROLE_ADMIN") // security 설정 파일 없이도 간단하게 권한을 걸 수 있음
    @ResponseBody
    public String info() {
        return "개인정보";
    }

    @GetMapping("/data")
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") // 여러개의 권한을 걸 때 사용
    @ResponseBody
    public String data() {
        return "데이터정보";
    }

}
