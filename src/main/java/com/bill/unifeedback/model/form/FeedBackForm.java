package com.bill.unifeedback.model.form;

import com.bill.unifeedback.validator.ValidReCaptcha;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class FeedBackForm {

    @NotNull
    private Long issueId;
    @NotBlank(message = "Name must  not be empty ")
    private String name;
    @NotBlank(message = "email must not be empty")
    @Email(message = "Please specify a valid email")
    private String email;
    @NotBlank(message = "feedback must not be empty")
    private String feedback;
    @Nullable
    private Long id;
    @ValidReCaptcha
    @NotBlank(message = "captcha must not be empty")
    private String reCaptchaResponse;

}