package ro.utcn.ps.mi.assignment1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.utcn.ps.mi.assignment1.dto.UserDto;
import ro.utcn.ps.mi.assignment1.entity.AppUserRole;
import ro.utcn.ps.mi.assignment1.entity.User;
import ro.utcn.ps.mi.assignment1.service.UserService;

import java.sql.SQLIntegrityConstraintViolationException;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value="/register")
public class RegisterController {
    private final UserService service;
    @PostMapping
    public String register(@RequestBody UserDto user){
        try{
            service.save(user,"");
            return "welcome";
        }catch (Exception e){
            return "something went wrong";
        }

    }
}
