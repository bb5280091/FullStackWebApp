package com.adoption.configuration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Component
@Configuration
@EnableWebMvc
public class CORSFilter implements Filter {

  private final List<String> allowedOrigins = Arrays.asList("http://localhost:4200");

  public void destroy() {

  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
      HttpServletRequest request = (HttpServletRequest) req;
      HttpServletResponse response = (HttpServletResponse) res;
      String origin = request.getHeader("Origin");
      response.setHeader("Access-Control-Allow-Origin",
          allowedOrigins.contains(origin) ? origin : "http://localhost:4200");
      response.setHeader("Vary", "Origin");
      response.setHeader("Access-Control-Max-Age", "3600");
      response.setHeader("Access-Control-Allow-Credentials", "true");
      response.setHeader("Access-Control-Allow-Methods", "*");
      response.setHeader("Access-Control-Allow-Headers",
          "Origin, X-Requested-With, Content-Type, Accept," + "X-csrf-token");
    }
    chain.doFilter(req, res);
  }

  public void init(FilterConfig filterConfig) {

  }

}
