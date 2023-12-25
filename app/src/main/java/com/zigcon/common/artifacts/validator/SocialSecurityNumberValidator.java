package com.zigcon.common.artifacts.validator;

/**
 * @Author Dharmesh
 * @Date 24-02-2022
 * <p>
 * Information
 **/
public class SocialSecurityNumberValidator {

    private String number;

    public SocialSecurityNumberValidator(String data) {
        this.number = data;
    }

    public boolean isSocialSecurityNumberEmpty() {
        return (number == null || number.isEmpty());
    }

    public boolean isSocialSecurityNumberInvalid() {
        return !((number.replaceAll("-", "")).length() == 9);
    }
}
