package com.example.security1.config;

import com.example.security1.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured, preAuthorize, postAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Bean // 해당 메소드의 리턴되는 오브젝트를 빈으로 등록
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder(); // 패스워드 암호화 객체를 리턴함
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                // 로그인한 사용자만 접근 가능
                .antMatchers("/user/**").authenticated()
                // ROLE_ADMIN나 ROLE_MANAGER 권한이 있어야만 접근 가능
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                // ROLE_ADMIN 권한이 있어야만 접근 가능
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll() // 그 외의 경로는 누구나 접근 가능
                .and()
                .formLogin()
                .loginPage("/loginForm") // 권한이 없는 페이지에 접근시 /loginForm으로 이동
                .loginProcessingUrl("/login") // /login 주소가 호출이 되면 시큐리티가 로그인을 대신 진행함.
                .defaultSuccessUrl("/")
                .and()
                .oauth2Login()
                .loginPage("/loginForm") // Tip. 코드 x (엑세스토큰 + 사용자 프로필 정보) o
                .userInfoEndpoint()
                .userService(principalOauth2UserService);

    }
}