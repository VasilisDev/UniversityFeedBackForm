package com.bill.unifeedback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UnifeedbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnifeedbackApplication.class, args);
    }

}
