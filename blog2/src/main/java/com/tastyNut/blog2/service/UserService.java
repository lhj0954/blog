package com.tastyNut.blog2.service;

import com.tastyNut.blog2.model.RoleType;
import com.tastyNut.blog2.model.User;
import com.tastyNut.blog2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public void 회원가입(User user) {

        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole(RoleType.USER);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User 회원찾기(String username) {

        User user = userRepository.findByUsername(username).orElseGet(()->{
            return new User();
        });
        return user;
    }

    @Transactional
    public void 회원수정(User user) {
        User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
            return new  IllegalArgumentException("회원 찾기 실패");
        });

        if (persistance.getOauth() == null || persistance.getOauth().equals("")) {
            String rawPassword = user.getPassword();
            String encPassword = encoder.encode(rawPassword);
            persistance.setPassword(encPassword);
            persistance.setEmail(user.getEmail());
        }
    }
}
