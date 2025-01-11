package com.bcodes.todo.service;

import com.bcodes.todo.model.MyUser;
import com.bcodes.todo.repository.MyUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final MyUserRepository myUserRepository;

    public MyUserDetailsService(MyUserRepository myUserRepository) {
        this.myUserRepository = myUserRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> user = myUserRepository.findUserByUsername(username);
        if (user.isPresent()){
            var userObj = user.get();
            return User.builder()
                    .username(userObj.username())
                    .password(userObj.password())
                    .roles("USER")
                    .build();
        }
        else {
            throw new UsernameNotFoundException(username);
        }
    }
}
