package com.adoption.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import com.adoption.entity.UserEntity;
import com.adoption.exception.AccountDisabledException;
import com.adoption.repository.UserRepository;
import com.adoption.security.CustomOidcUser;
import com.adoption.security.JwtUtil;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@Service
public class OdicUserServiceImpl extends OidcUserService {
  private static final long serialVersionUID = 1L;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtUtil jwtUtil;

  @Override
  public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
    String realName;
    Long userId;
    String role;
    // 先查詢資料庫是否有登記的紀錄
    String mail = super.loadUser(userRequest).getEmail();
    List<UserEntity> userDetailsData = userRepository.findByGoogleAccount(mail);
    realName = (String) super.loadUser(userRequest).getAttributes().get("given_name")
        + super.loadUser(userRequest).getAttributes().get("family_name");

    // 若無登入紀錄則新增一筆資料
    if (userDetailsData.isEmpty()) {
      log.info("Login via Google without any registration record");
      // 放入資料庫當中
      UserEntity userEntity = new UserEntity();
      userEntity.setName(realName);
      userEntity.setGoogleAccount(mail);
      userEntity.setRole("USER");
      userEntity.setStatus("Y");
      userRepository.saveAndFlush(userEntity);
      // 因userId是進到資料庫當中會進行自動編號故需再查詢一次確認資料
      List<UserEntity> newUserDetailsData = userRepository.findByGoogleAccount(mail);
      UserEntity newUserDetails = newUserDetailsData.get(0);
      userId = newUserDetails.getUserId();
      role = newUserDetails.getRole();
    } else {
      log.info("Logged in via Google with a registration record");
      // 將要回傳所需資料取得完畢
      UserEntity newUserDetails = userDetailsData.get(0);
      userId = newUserDetails.getUserId();
      role = newUserDetails.getRole();
      System.out.println((newUserDetails.getStatus()));
      // 被封鎖則停止則跳出製作UserService的過程
      if ("N".equals(newUserDetails.getStatus())) {
        throw new AccountDisabledException("Account is disable");
      }
    }
    
    // 將獲得的DefaultOidcUser轉換為OidcUser後回傳與security
    return new CustomOidcUser(super.loadUser(userRequest), tranferRole(role), userId);
  }

  public OidcUser loadUserByJwt(String jwt) {
    Map<String, Object> claimsMap = jwtUtil.getClaimsFromToken(jwt);

    String role = jwtUtil.extractRole(jwt);
    OidcIdToken oidcIdToken =
        new OidcIdToken(jwt, Instant.now(), Instant.now().plusSeconds(3600), claimsMap);
    OidcUserInfo userInfo = new OidcUserInfo(claimsMap);

    // 將獲得的DefaultOidcUser轉換為OidcUser後回傳與security
    DefaultOidcUser defaultOidcUser = new DefaultOidcUser(tranferRole(role), oidcIdToken, userInfo);
    return defaultOidcUser;
  }

  // Role轉換製造機
  public Collection<? extends GrantedAuthority> tranferRole(String role) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
    return authorities;
  }

}
