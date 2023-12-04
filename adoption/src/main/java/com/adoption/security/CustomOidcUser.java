package com.adoption.security;

import java.util.Collection;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public class CustomOidcUser implements OidcUser {

  private OidcUser oidcUser ;
  private Collection<? extends GrantedAuthority> role;

  private Long userId;

  public CustomOidcUser(OidcUser oidcUser, Collection<? extends GrantedAuthority> role, Long userId) {
    this.role = role ;
    this.userId= userId ;
    this.oidcUser = oidcUser ;
  }


  @Override
  public Map<String, Object> getAttributes() {
    return this.oidcUser.getAttributes() ;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.role;
  }

  @Override
  public String getName() {
    return this.oidcUser.getEmail();
  }

  @Override
  public Map<String, Object> getClaims() {
    return this.oidcUser.getClaims();
  }

  @Override
  public OidcUserInfo getUserInfo() {
    return this.oidcUser.getUserInfo();
  }

  @Override
  public OidcIdToken getIdToken() {
    return this.oidcUser.getIdToken();
  }
  
  public String getMail() {
    return this.oidcUser.getEmail();
  }

  public Long getUserId() {
    return this.userId;
  }
}
