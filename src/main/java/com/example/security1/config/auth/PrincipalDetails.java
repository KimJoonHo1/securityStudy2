package com.example.security1.config.auth;


// 시큐리티가 /login 주소로 요청이 오면 낚아채서 로그인을 진행
// 로그인이 완료되면 시큐리티 session을 만들어야함. (Key = Security ContextHolder)
// Value = Authentication 객체
// Authentication 안에 User 정보가 있어야 됨
// User 객체는 UserDetails 객체가 있어야함
// Authentication => UserDetails(PrincipalDetails)

import com.example.security1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails { // UserDetails을 구현한 객체

    private User user; // 콤포지션

    public PrincipalDetails(User user) {
        this.user = user;
    }

    
    // 해당 유저의 권한을 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    // 해당 유저의 패스워드를 리턴
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 해당 유저의 username을 리턴
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
