package com.bill.unifeedback.controller;

import java.util.function.Function;

class ControllerHelper {
    static Function<String, String> redirect = (path)-> "redirect:" + path;
}