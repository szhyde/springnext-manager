package org.springnext.manager.base.security;

import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException {

	public ValidateCodeException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
