package com.adoption.security;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.adoption.exception.AccountDisabledException;
import com.adoption.service.impl.OdicUserServiceImpl;
import lombok.extern.slf4j.Slf4j;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@CrossOrigin
@Slf4j
@Configuration
@EnableWebSecurity
public class Oauth2LoginSecurityCinfig {

  @Autowired
  private OdicUserServiceImpl odicUserServiceImpl;

  @Autowired
  private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  
  @Autowired
  private JwtRequestFilter jwtRequestFilter;
  
  @Autowired
  private JwtUtil jwtUtil;
 
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors().and().authorizeHttpRequests(
        authz -> authz.antMatchers("/**").permitAll().anyRequest().authenticated())
        .oauth2Login(oauth2Login -> {
          oauth2Login.userInfoEndpoint(userInfoEndpoint -> {
            userInfoEndpoint.oidcUserService(odicUserServiceImpl);
          }).successHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request,
                HttpServletResponse response, Authentication authentication)
                throws IOException, ServletException {
              log.info("Login via Google and authorization Role: "+authentication.getAuthorities());
              String token = jwtUtil.generateToken(authentication);
              log.info("Login via Google and get token "+ token);
              response.sendRedirect("http://localhost:4200?token="+ URLEncoder.encode(token, "UTF-8"));
            }
          }).failureHandler((request, response, exception) ->{
            if(exception instanceof AccountDisabledException ) {
              response.sendRedirect("http://localhost:4200?accountDisable=true");
            }
          });
        })
        //將session授權關閉，全部使用JWT token的方式訪問
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        //Request進來後測試是否為攜帶token若無則檢查該api是否受保護，若有則進入Filter檢查
        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        .csrf(AbstractHttpConfigurer::disable);

    return http.build();
  }

  //跨站處理
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
      CorsConfiguration configuration = new CorsConfiguration();
      configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // 允許的來源
      configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
      configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Content-Type", "Authorization", "Origin", "Accept"));
      configuration.setAllowCredentials(true);
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", configuration);
      return  (CorsConfigurationSource)source; 
  }
}
