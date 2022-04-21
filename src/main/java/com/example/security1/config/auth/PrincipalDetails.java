package com.example.security1.config.auth;


// 시큐리티가 /login 주소로 요청이 오면 낚아채서 로그인을 진행
// 로그인이 완료되면 시큐리티 session을 만들어야함. (Key = Security ContextHolder)
// Value = Authentication 객체
// Authentication 안에 User 정보가 있어야 됨
// User 객체는 UserDetails 객체가 있어야함
// Authentication => UserDetails(PrincipalDetails)

import com.example.security1.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User { // UserDetails, OAuth2User을 구현한 객체

    private User user; // 콤포지션
    private Map<String, Object> attributes; // Oauth 로그인시 구글 사용자 프로필 정보

    /**
     * 일반 로그인 생성자
     * @param user model
     */
    public PrincipalDetails(User user) {
        this.user = user;
    }

    /**
     *  OAuth 로그인 생성자
     * @param user model
     * @param attirubtes 구글 프로필 정보
     */
    public PrincipalDetails(User user, Map<String, Object> attirubtes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * 해당 유저의 권한을 리턴
     * @return Collection
     */
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
