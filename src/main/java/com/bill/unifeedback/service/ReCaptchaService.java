package com.bill.unifeedback.service;

public interface ReCaptchaService {

    boolean processResponse(final String response) ;

    String getReCaptchaKey();

    String getReCaptchaSecret();
}
