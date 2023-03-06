package ro.utcn.ps.mi.assignment1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.utcn.ps.mi.assignment1.dto.UserDto;
import ro.utcn.ps.mi.assignment1.entity.AppUserRole;
import ro.utcn.ps.mi.assignment1.entity.User;
import ro.utcn.ps.mi.assignment1.persistance.api.RepositoryFactory;


import javax.transaction.Transactional;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class UserService{
    private  final RepositoryFactory repositoryFactory;
    private  final PasswordEncoder passwordEncoder;
    @Transactional
    public void save(UserDto user,String l) {
        User u = repositoryFactory.createUserReposytory().findByUsername(user.getUsername()).orElseThrow(UserNotFoundException::new);
        if (u != null) {
            u.setName(user.getName());
            u.setEmail(user.getEmail());
            if(!l.equals("login")) {
                u.setPassword(passwordEncoder.encode(user.getPassword()));
            }else{
                u.setPassword(user.getPassword());
            }
            u.setScore(user.getScore());
            repositoryFactory.createUserReposytory().save(u);
        } else{
            User user1 = new User(user.getName(), user.getUsername(), user.getEmail(),passwordEncoder.encode(user.getPassword()), AppUserRole.USER, false, true, user.getScore());
        repositoryFactory.createUserReposytory().save(user1);
         }
    }
    @Transactional
    public UserDto findByUsername(String username){
        User user=repositoryFactory.createUserReposytory().findByUsername(username).orElseThrow(UserNotFoundException::new);
        return UserDto.userDtoFromUser(user);
    }
    @Transactional
    public void removeUser(UserDto  user){
        User user1 = repositoryFactory.createUserReposytory().findByUsername(user.getUsername()).orElseThrow(UserNotFoundException::new);
        repositoryFactory.createUserReposytory().remove(user1);
    }

    @Transactional
    public UserDto getUserById(Integer id){
        User user =  repositoryFactory.createUserReposytory().findById(id).orElseThrow(UserNotFoundException::new);
        return UserDto.userDtoFromUser(user);
    }


}
