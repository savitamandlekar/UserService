package com.in.user.Exception;

public class ResourceNotFoundException extends RuntimeException{

    public  ResourceNotFoundException(String s){
        super("Resource not found");
    }


}
