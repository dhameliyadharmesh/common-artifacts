package com.zigcon.common.artifacts.validator;

/**
 * @Author Dharmesh
 * @Date 24-02-2022
 * <p>
 * Information
 **/
public class ZipCodeValidator {

    private String zipcode;

    public ZipCodeValidator(String email) {
        this.zipcode = email;
    }

    public boolean isZipCodeEmpty() {
        return (zipcode == null || zipcode.isEmpty());
    }

    public boolean isZipCodeInValid(){
        return !(zipcode.length() == 5);
    }
}
