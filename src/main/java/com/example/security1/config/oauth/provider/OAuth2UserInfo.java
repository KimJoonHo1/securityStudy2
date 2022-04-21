package com.example.security1.config.oauth.provider;

// 사용자 프로필 정보 인터페이스
public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();

}
