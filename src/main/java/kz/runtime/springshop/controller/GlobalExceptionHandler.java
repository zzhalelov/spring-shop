package kz.runtime.springshop.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public String noSuchElementExceptionHandler(EntityNotFoundException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "not_found";
    }
}