package ro.utcn.ps.mi.assignment1.dto;

import lombok.Data;
import ro.utcn.ps.mi.assignment1.entity.Answare;
import ro.utcn.ps.mi.assignment1.entity.AppUserRole;
import ro.utcn.ps.mi.assignment1.entity.Question;
import ro.utcn.ps.mi.assignment1.entity.User;

import javax.persistence.*;
import java.util.List;

@Data
public class UserDto {

    private String  name;
    private String username;
    private String email;
    private String password;
    private Integer score;

    public static UserDto userDtoFromUser(User user){
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setScore(user.getScore());
        return  userDto;
    }
}
