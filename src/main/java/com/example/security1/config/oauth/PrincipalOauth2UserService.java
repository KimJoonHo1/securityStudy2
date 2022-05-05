package com.example.security1.config.oauth;

import com.example.security1.config.auth.PrincipalDetails;
import com.example.security1.config.oauth.provider.FacebookUserInfo;
import com.example.security1.config.oauth.provider.GoogleUserInfo;
import com.example.security1.config.oauth.provider.NaverUserInfo;
import com.example.security1.config.oauth.provider.OAuth2UserInfo;
import com.example.security1.model.User;
import com.example.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    /**
     * 구글 Oauth2 로그인 후 후처리가 실행되는 함수
     * AccessToken : 구글 사용자 로그인후 사용자 프로필 접근 객체
     * 메소드 종료후 @AuthenticationPrincipal이 만들어진다
     * @param userRequest 구글로부터 받은 엑세스토큰
     * @return
     * @throws OAuth2AuthenticationException
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 어떤 OAuth로 로그인 했는지에 대한 정보
        System.out.println("getClientRegistration : " + userRequest.getClientRegistration());
        System.out.println("getAccessToken : " + userRequest.getAccessToken().getTokenValue());

        OAuth2User oAuth2User = super.loadUser(userRequest); // 구글 사용자 프로필을 가져오는 메소드 호출
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

        // 구글 로그인 완료 -> code 리턴(OAuth-Client 라이브러리) -> AccessToken 요청
        // 여기까지가 userRequest 정보-> loadUser 메소드 호출 -> 회원 프로필
        System.out.println("getAttributes : " + super.loadUser(userRequest).getAttributes());

        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
            oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            oAuth2UserInfo = new NaverUserInfo((Map) oAuth2User.getAttributes().get("response"));
        }else {
            System.out.println("구글, 페이스북, 네이버만 지원합니다.");
        }

        // 강제 회원가입
        // 구글 사용자 프로필 정보를 가져옴
        String provider = oAuth2UserInfo.getProvider(); // google
        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String username = provider + "_" + providerId; // google_114045794135395764311
        String password = bCryptPasswordEncoder.encode("겟인데어");
        String role = "ROLE_USER";

        User userEntity =  userRepository.getUser(username); // 이미 등록된 회원인지 검사

        if(userEntity == null) {
            System.out.println("OAuth 로그인이 최초입니다.");
            // 회원가입이 안 되었으므로 받아온 구글 사용자 프로필 정보를 model에 바인딩
            userEntity = User.builder().username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity); // 바인딩한 회원정보를 DB에 저장
        } else {
            System.out.println("로그인을 이미 한적이 있습니다. 당신은 자동으로 회원가입이 된 상태입니다.");
        }

        return new PrincipalDetails(userEntity, oAuth2User.getAttributes()); // 세션에 저장
    }
}