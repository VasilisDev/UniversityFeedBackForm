package com.bill.unifeedback.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.bill.unifeedback.controller.ControllerHelper.redirect;

@Controller
public class DefaultController {
    @GetMapping( "/")
    public String defaultController() {
        return redirect.apply("/issue/all");
    }
}
