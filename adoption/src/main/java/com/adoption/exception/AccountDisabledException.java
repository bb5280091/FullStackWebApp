package com.adoption.exception;

import org.springframework.security.core.AuthenticationException;

public class AccountDisabledException extends AuthenticationException {
  
  private static final long serialVersionUID = 1L;

  public AccountDisabledException(String msg) {
    super(msg);
  }

}
