package com.bill.unifeedback.service.impl;

import com.bill.unifeedback.captcha.CaptchaSettings;
import com.bill.unifeedback.exceptions.ReCaptchaInvalidException;
import com.bill.unifeedback.exceptions.ReCaptchaUnavailableException;
import com.bill.unifeedback.response.ReCaptchaResponse;
import com.bill.unifeedback.service.ReCaptchaAttemptService;
import com.bill.unifeedback.service.ReCaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.regex.Pattern;

@Service
public class ReCaptchaServiceImpl implements ReCaptchaService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ReCaptchaService.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private CaptchaSettings captchaSettings;
    @Autowired
    ReCaptchaAttemptService reCaptchaAttemptService;


    @Autowired
    private RestOperations restTemplate;
    private static final Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");



    @Override
    public boolean processResponse(String response) {

        if (reCaptchaAttemptService.isBlocked(getClientIP())) {
            throw new ReCaptchaInvalidException("Client exceeded maximum number of failed attempts");
        }

        if (!responseSanityCheck(response)) {
            throw new ReCaptchaInvalidException("Response contains invalid characters");
        }
        final URI verifyUri = URI
                                 .create(String.format("https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s",
                                                 getReCaptchaSecret(),
                                                 response,
                                                 getClientIP()));

        try {
            ReCaptchaResponse googleResponse = restTemplate.getForObject(verifyUri, ReCaptchaResponse.class);
            assert googleResponse != null;
            LOGGER.debug("Google's response: {} ", googleResponse.toString());

            if (!googleResponse.isSuccess()) {
                if (googleResponse.hasClientError()) {
                    reCaptchaAttemptService.reCaptchaFailed(getClientIP());
                }
                throw new ReCaptchaInvalidException("reCaptcha was not successfully validated");
            }
            reCaptchaAttemptService.reCaptchaSucceeded(getClientIP());
            return true;
        } catch (RestClientException ex){
            throw new ReCaptchaUnavailableException("Registration unavailable at this time.  Please try again later.", ex);
         }
    }

    @Override
    public String getReCaptchaKey() {
        return  captchaSettings.getKey();
    }

    @Override
    public String getReCaptchaSecret() {
        return captchaSettings.getSecret();
    }

    private boolean responseSanityCheck(final String response) {
        return StringUtils.hasLength(response) && RESPONSE_PATTERN.matcher(response).matches();
    }

    private String getClientIP() {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
