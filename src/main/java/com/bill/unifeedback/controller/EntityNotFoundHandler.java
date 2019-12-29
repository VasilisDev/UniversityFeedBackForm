package com.bill.unifeedback.controller;

import com.bill.unifeedback.exceptions.EntityNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class EntityNotFoundHandler {

    @ExceptionHandler(EntityNotFound.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    public ModelAndView notFound(HttpServletRequest req, EntityNotFound ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("problem", ex.getMessage());
        mav.setViewName("error");
        return mav;
    }
}

