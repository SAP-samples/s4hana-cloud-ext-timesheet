package com.sap.csc.timebackend.security;

import static com.sap.cloud.security.xsuaa.token.TokenClaims.CLAIM_USER_NAME;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class CFAuthFacade implements AuthenticationFacade {

	@Override
	public SAPUser getLoggedUser(Jwt jwt) {
		return SAPUser.create(jwt.getClaimAsString(CLAIM_USER_NAME));
	}
}
