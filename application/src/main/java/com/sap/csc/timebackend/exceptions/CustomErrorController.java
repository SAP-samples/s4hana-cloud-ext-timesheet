package com.sap.csc.timebackend.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.web.ErrorAttributes;
//import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";
    private final ErrorAttributes errorAttributes;

    @Autowired
    public CustomErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

//    @GetMapping(PATH)
//    CustomError error(HttpServletRequest request, HttpServletResponse response) {
//        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
//        Map<String, Object> attributes = errorAttributes.getErrorAttributes(requestAttributes, false);
//        return new CustomError(HttpStatus.valueOf(response.getStatus()), (String) attributes.get("message"), request);
//    }

    @GetMapping(PATH)
    CustomError error(ServletWebRequest request, HttpServletResponse response) {
        Map<String, Object> attributes = errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.defaults().including(ErrorAttributeOptions.Include.MESSAGE));
        return new CustomError(HttpStatus.valueOf(response.getStatus()), (String) attributes.get("message"), request.getRequest());
    }


    public String getErrorPath() {
        return PATH;
    }
}