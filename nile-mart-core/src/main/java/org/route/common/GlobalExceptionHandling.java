package org.route.common;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandling {
    @ExceptionHandler(StoreException.class)
    public String handleException(StoreException e, Model model, HttpServletResponse response) {
        response.setStatus(e.statusCode());
        model.addAttribute("status", e.statusCode());
        model.addAttribute("message", e.getMessage());
        return "error";
    }
}
