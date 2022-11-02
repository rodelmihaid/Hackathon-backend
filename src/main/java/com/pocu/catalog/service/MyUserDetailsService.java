package com.pocu.catalog.service;

import com.pocu.catalog.entity.User;
import com.pocu.catalog.entity.UserPrincipal;
import com.pocu.catalog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    //    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        List<User> user = userRepository.findUserByEmail(email);
//        String encodedUserPassword = bCryptPasswordEncoder.encode();
        if(user.size()==1){
            return new UserPrincipal(user.get(0));
        }
        throw new RuntimeException("nu ar trebui sa se intampla asta");

//        return new User("admin", encodedUserPassword, new ArrayList<>());
    }
}
