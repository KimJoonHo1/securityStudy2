package com.example.security1.repository;

import com.example.security1.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserRepository{

    /**
     * 로그인
     * @param username username
     * @return User
     */
    public User getUser(@Param("username") String username);

    /**
     * 회원가입
     * @param user 회원 정보 파라미터
     * @return int
     */
    public int save(@Param("user") User user);

}
