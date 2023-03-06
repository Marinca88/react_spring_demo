package ro.utcn.ps.mi.assignment1.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.utcn.ps.mi.assignment1.entity.User;
import ro.utcn.ps.mi.assignment1.persistance.api.UserRepository;

import javax.transaction.Transactional;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Unkown user"));
       return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getAppUserRole().name().toLowerCase())));
    }

    @Transactional
    public User loadCurrentUser(){
        String  username  = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }


}
