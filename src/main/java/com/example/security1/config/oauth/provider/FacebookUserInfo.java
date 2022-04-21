package com.example.security1.config.oauth.provider;

import java.util.Map;

// 페이스북 사용자 프로필 정보
public class FacebookUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes; // Oauth2user 사용자 프로필 정보

    public FacebookUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getProvider() {
        return "facebook";
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}
