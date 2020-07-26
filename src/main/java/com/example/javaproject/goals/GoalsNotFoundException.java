package com.example.javaproject.goals;

public class GoalsNotFoundException extends RuntimeException{
    public GoalsNotFoundException(String exception){
        super(exception);
    }
}
