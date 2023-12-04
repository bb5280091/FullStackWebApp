package com.adoption.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@Component
public class JwtUtil {

  // 定義私鑰，簽署和驗證JWT
  private String SECRET_KEY = "YOUR_SECRET_KEY";

  // 從JWT中拿取Username
  public String extractUsername(String token) {
    System.out.println("userName :" + extractClaim(token, Claims::getSubject));
    return extractClaim(token, Claims::getSubject);
  }

  // 從JWT中提取userId
  public Long extractUserId(String token) {
    Number number = (Number) extractClaim(token, claims -> claims.get("userId"));
    return number.longValue();
  }

  // 從JWT中提取role
  public String extractRole(String token) {
    return (String) extractClaim(token, claims -> claims.get("role"));
  }

  // 從JWT中提取過期時間
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  // 從JWT中提取特定的聲明。這是一個通用方法，根據claimsResolver提取信息
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  // 從JWT中提取所有聲明
  private Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
  }

  // 從JWT當中提取所有聲明但使用map包裝
  public Map<String, Object> getClaimsFromToken(String token) {
    Claims claims = extractAllClaims(token);
    return claims;
  }

  // 檢查JWT是否過期
  public Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  // 根據認證信息生成JWT
  public String generateToken(Authentication authentication) {

    Map<String, Object> claims = new HashMap<>();
    CustomOidcUser customUser = (CustomOidcUser) authentication.getPrincipal();
    // 取得role
    claims.put("role", customUser.getAuthorities().stream().map(GrantedAuthority::getAuthority)
        .filter(r -> r.startsWith("ROLE_")).findFirst().orElse("DEFAULT_ROLE").substring(5));
    claims.put("userId", customUser.getUserId());
    claims.put("realname", customUser.getFullName());
    return createToken(claims, authentication.getName());
  }

  // 創建JWT
  private String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder().setClaims(claims).setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 這裡設置token的有效期為10小時
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
  }

  //驗證token
  public Boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
