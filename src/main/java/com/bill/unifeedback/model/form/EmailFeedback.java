package com.bill.unifeedback.model.form;

import com.bill.unifeedback.validator.ValidReCaptcha;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class EmailFeedback implements Serializable {

    @Email(message = "Sender's email must be valid")
    @NotBlank(message = "Sender's email must not be empty")
    private String senderEmail;
    @Nullable
    private Long issueId;
    @Nullable
    private Long feedbackId;
    @NotBlank(message = "Subject must not be empty")
    private String subject;
    @NotBlank(message = "Body must not be empty")
    private String body;
    @Email(message = "Receiver's email must be valid")
    @NotBlank(message = "Receiver's email must not be empty")
    private String receiverEmail;
    @ValidReCaptcha
    @NotBlank(message = "captcha must not be empty")
    private String reCaptchaResponse;
}
