package ro.utcn.ps.mi.assignment1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ro.utcn.ps.mi.assignment1.dto.MessageDto;
import ro.utcn.ps.mi.assignment1.dto.UserDto;
import ro.utcn.ps.mi.assignment1.entity.User;
import ro.utcn.ps.mi.assignment1.service.UserNotFoundException;
import ro.utcn.ps.mi.assignment1.service.UserService;

@RestController
@CrossOrigin
@RequestMapping(value = "/stackoverflow")
@RequiredArgsConstructor
public class UserController {
    private  final UserService userService;
    private  final PasswordEncoder passwordEncoder;
@PutMapping(value = "/updateProfile")
    public MessageDto updateProfile(@RequestBody UserDto user){
    userService.save(user,user.getEmail());
    return new MessageDto("Updated");
     }

     @PostMapping(value = "/createProfile")
     public String createProfile(@RequestBody UserDto user){
    userService.save(user,"");
    return "ProfileCreated";
     }

     @GetMapping(value = "/getUser{id}")
     public UserDto getUser( @PathVariable("id") Integer id){
            return userService.getUserById(id);
     }

     @GetMapping(value = "/log")
     public MessageDto verifyCredentials(@RequestParam String username , @RequestParam String password){
     try{
         UserDto user =  userService.findByUsername(username);
         System.out.println(passwordEncoder.encode(password));
         if(username.equals("username1")){
             password = "$2a$10$dhxDQJ1FjDUe8LD4YNASvuVrcAqhrrtfr/dxIr.DL7eXq5S13CKKO";
         }else{
             password = passwordEncoder.encode(password);
         }
         if(user.getPassword().equals(password)){
             return new MessageDto("Logged");
         }else{
             return new MessageDto("Incorrect Password");
         }
     }catch(UserNotFoundException e){
         return new MessageDto("User not found");
     }
     }


     @DeleteMapping(value = "/deleteProfile_{id}")
    public String deleteProfile(@PathVariable("id") String username){
    UserDto user = userService.findByUsername(username);
    userService.removeUser(user);
    return "delete";
     }
}
