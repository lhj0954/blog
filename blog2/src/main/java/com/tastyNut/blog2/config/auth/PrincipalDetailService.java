package com.tastyNut.blog2.config.auth;

import com.tastyNut.blog2.model.User;
import com.tastyNut.blog2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public PrincipalDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = userRepository.findByUsername(username)
                .orElseThrow(()->{
                    return new UsernameNotFoundException("해당 회원을 찾을 수 없습니다 : " + username);
                });
        return new PrincipalDetail(principal);
    }
}
