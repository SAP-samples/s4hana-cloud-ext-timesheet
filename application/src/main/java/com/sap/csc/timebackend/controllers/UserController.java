package com.sap.csc.timebackend.controllers;

import com.sap.csc.timebackend.security.AuthenticationFacade;
import com.sap.csc.timebackend.security.SAPUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sap.cloud.security.token.SecurityContext;
import com.sap.cloud.security.xsuaa.tokenflows.XsuaaTokenFlows;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;



@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final AuthenticationFacade authenticationFacade;

    @Autowired
    public UserController(AuthenticationFacade authenticationFacade, XsuaaTokenFlows tokenFlows) {
        this.authenticationFacade = authenticationFacade;
    }

    @GetMapping
    public SAPUser getAuthUser(@AuthenticationPrincipal Jwt jwt) {
        return authenticationFacade.getLoggedUser(jwt);
    }

}
