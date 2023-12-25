package com.zigcon.common.artifacts.validator;

/**
 * @Author Dharmesh
 * @Date 24-02-2022
 * <p>
 * Information
 **/
public class AnyFieldValidator {

    private String data;

    public AnyFieldValidator(String data) {
        this.data = data;
    }

    public boolean isFieldEmpty() {
        return (data == null || data.isEmpty());
    }

}
