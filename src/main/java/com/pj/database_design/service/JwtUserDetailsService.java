package com.pj.database_design.service;

import com.pj.database_design.domain.Authority;
import com.pj.database_design.domain.User;
import com.pj.database_design.repository.UserRepository;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author zyl
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;


    public JwtUserDetailsService(UserRepository userRepository){
                         this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByName(username);
        if (null == user) {
            throw new UsernameNotFoundException("User: '" + username + "' not found.");
        }
        return new User(user.getUsername(), user.getPassword(), user.getAge(), user.getGender(),
                (Set<Authority>) user.getAuthorities());
    }
}
