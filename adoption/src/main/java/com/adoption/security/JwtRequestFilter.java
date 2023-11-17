package com.adoption.security;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.adoption.service.impl.OdicUserServiceImpl;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private OdicUserServiceImpl odicUserServiceImpl;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    // 從請求頭中提取JWT
    final String authorizationHeader = request.getHeader("Authorization");

    String username = null;
    String jwt = null;

    // 檢查header是否存在並以"Bearer "開頭
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      jwt = authorizationHeader.substring("Bearer".length()); // 從"Bearer"後開始提取JWT
      username = jwtUtil.extractUsername(jwt); // 從JWT中提取用戶名
      log.info("token received" + jwt);
      log.info("Request user mail" + username);
    }

    // 在token不為null的情況下進行檢查
    if (jwt != null) {
      // 檢查JWT token的有效性若無效則拋出Exception
      if (Boolean.FALSE.equals(jwtUtil.validateToken(jwt))) {
        throw new BadCredentialsException("Invalid JWT token");
      }
      // 檢查JWT 是否過期
      if (Boolean.TRUE.equals(jwtUtil.isTokenExpired(jwt))) {
        throw new BadCredentialsException("Invalid JWT token");
      }
      // 如果JWT有效則發出相關的驗證
      if (SecurityContextHolder.getContext().getAuthentication() == null) {
        OidcUser oidcUser = odicUserServiceImpl.loadUserByJwt(jwt);
        Collection<? extends GrantedAuthority> authorities = oidcUser.getAuthorities();
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(oidcUser, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
    }
    filterChain.doFilter(request, response);
  }
}


