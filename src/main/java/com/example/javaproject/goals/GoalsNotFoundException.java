package com.example.javaproject.goals;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GoalsNotFoundException extends RuntimeException{
    private String message;
    /*public GoalsNotFoundException(String exception){
        super(exception);
    }*/
    public GoalsNotFoundException(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
