package com.example.security1.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
public class User {
    private String username;
    private String role;
    private String password;
    private int id;
    private String email;
    private Timestamp createDate;
    private String provider; // 구글 로그인 시 "google"가 들어감
    private String providerId; // 구글 계정 PK값

    @Builder
    public User(String username, String role, String password, String email, Timestamp createDate, String provider, String providerId) {
        this.username = username;
        this.role = role;
        this.password = password;
        this.email = email;
        this.createDate = createDate;
        this.provider = provider;
        this.providerId = providerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}