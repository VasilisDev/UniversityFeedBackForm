package com.bill.unifeedback.service;

import com.bill.unifeedback.model.form.EmailFeedback;

public interface EmailService {
    void sendSimpleMessage(EmailFeedback emailFeedback);
}
