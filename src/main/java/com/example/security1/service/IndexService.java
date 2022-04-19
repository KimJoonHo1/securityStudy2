package com.example.security1.service;

import com.example.security1.model.User;
import com.example.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class IndexService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    /**
     * 회원가입 메소드
     * @param username username
     * @param password password
     * @param email email
     */
    public void join(String username, String password, String email) {
        User user = new User();

        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole("ROLE_USER");

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword); // 패스워드 암호화
        user.setPassword(encPassword);

        userRepository.save(user); // DB에 회원정보 저장
    }
}
