package com.bill.unifeedback.exceptions;

public class ReCaptchaInvalidException extends RuntimeException {
    public ReCaptchaInvalidException(String response_contains_invalid_characters) {
        super(response_contains_invalid_characters);
    }
}
