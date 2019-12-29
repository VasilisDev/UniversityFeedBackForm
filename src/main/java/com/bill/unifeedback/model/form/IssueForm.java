package com.bill.unifeedback.model.form;

import com.bill.unifeedback.validator.ValidReCaptcha;
import lombok.Data;
import org.springframework.lang.Nullable;


import javax.validation.constraints.NotBlank;


@Data
public class IssueForm {


    @Nullable
    private Long id;
    @NotBlank(message = "title must not be empty")
    private String title;
    @NotBlank(message = "description must not be empty")
    private String description;
    @ValidReCaptcha
    @NotBlank(message = "captcha must not be empty")
    private String reCaptchaResponse;
}
