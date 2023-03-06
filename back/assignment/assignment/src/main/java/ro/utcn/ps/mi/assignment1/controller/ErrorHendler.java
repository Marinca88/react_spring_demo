package ro.utcn.ps.mi.assignment1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ro.utcn.ps.mi.assignment1.dto.ErrorDto;
import ro.utcn.ps.mi.assignment1.service.UserNotFoundException;

@Controller
@RestControllerAdvice
public class ErrorHendler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorDto handleNPE(UserNotFoundException ex){
        return new ErrorDto("User not found");
    }
}
