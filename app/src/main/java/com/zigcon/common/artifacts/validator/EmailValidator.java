package com.zigcon.common.artifacts.validator;

import android.util.Patterns;

/**
 * @Author Dharmesh
 * @Date 24-02-2022
 * <p>
 * Information
 **/
public class EmailValidator {

    private String email;

    public EmailValidator(String email) {
        this.email = email;
    }

    public boolean isEmailEmpty() {
        return (email == null || email.isEmpty());
    }

    public boolean isEmailInValid(){
        return !Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
