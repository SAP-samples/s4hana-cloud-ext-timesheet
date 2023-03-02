package com.sap.csc.timebackend.security;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationFacade {

    SAPUser getLoggedUser(Jwt jwt);
}
