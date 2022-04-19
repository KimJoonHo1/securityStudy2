package com.example.security1.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    /**
     * 구글 Oauth2 로그인 후 후처리가 실행되는 함수
     * AccessToken : 구글 사용자 로그인후 사용자 프로필 접근 객체
     * @param userRequest 구글로부터 받은 엑세스토큰 + 사용자 프로필 정보
     * @return
     * @throws OAuth2AuthenticationException
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration : " + userRequest.getClientRegistration());
        System.out.println("getAccessToken : " + userRequest.getAccessToken().getTokenValue());
        System.out.println("getAttributes : " + super.loadUser(userRequest).getAttributes());
        /*
            sub : PK값
            name : 풀네임
            given_name : 이름
            family_name : 성
            picture : 사용자 프로필 이미지
            email : 이메일
            email_verified : 이메일 완료 여부
            locale : 지역
         */
        return super.loadUser(userRequest);
    }
}
