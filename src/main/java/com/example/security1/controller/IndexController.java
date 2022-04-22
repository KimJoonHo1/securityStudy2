package com.example.security1.controller;

import com.example.security1.config.auth.PrincipalDetails;
import com.example.security1.model.User;
import com.example.security1.repository.UserRepository;
import com.example.security1.service.IndexService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    @Autowired
    private IndexService service;

    /**
     *
     * @param authentication security session
     * @param userDetails 로그인한 사용자 정보
     * @return String
     * 세션 값은 Authentication 객체 또는 @AuthenticationPrincipal을 통해 가져올 수 있음
     */
    /*
    @GetMapping("/test/login")
    @ResponseBody
    //
    public String testLogin(Authentication authentication,
                            @AuthenticationPrincipal PrincipalDetails userDetails) {
        System.out.println("/test/login ================");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("authentication : " + principalDetails.getUser());

        System.out.println("userDetails : " + userDetails.getUser());

        return "세션 정보 확인하기";
    }
     */



    /**
     *
     * @param authentication security session
     * @return String
     * Oauth2로 로그인한 사용자의 세션 정보는 개발자가 정의한 모델이 아닌 OAuth2User 객체로 받아야함
     */

    @GetMapping("/test/oauth/login")
    @ResponseBody
    // Authentication 객체 또는 @AuthenticationPrincipal을 통해 세션 정보를 가져올 수 있음
    public String testOAuthLogin(Authentication authentication,
                                 @AuthenticationPrincipal OAuth2User oauth) {
        System.out.println("/test/oauth/login ================");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("authentication : " + oAuth2User.getAttributes());
        System.out.println("oauth2User : " + oauth.getAttributes());
        return "Oauth 세션 정보 확인하기";
    }



    @GetMapping({"", "/"})
    public String index() {
        // 머스테치 기본폴더 src/main/resources/
        return "index";
    }

    /**
     * OAuth 로그인을 해도 PrincipalDetails로 받아지고, 일반 사용자로 로그인해도 PrincipalDetails로 받아짐
     * @param principalDetails 로그인 정보
     * @return String
     */
    @GetMapping("/user")
    @ResponseBody
    public String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("principalDetails : " + principalDetails.getUser());
        return "user";
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

    @GetMapping("/user2")
    @ResponseBody
    public String user2() {
        return "나 불사조 김준호 성수동을 점령하러 왔다";
    }

}
