package com.example.gym.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class JwtService {

  @Value("${jwt.secret-key}")
  private String secretKey;

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

String generateToken(UserDetails userDetails){
    return generateToken(new HashMap<>(), userDetails);
  }


  public String generateToken(
      Map<String, Object> extraClaims, UserDetails userDetails
  )
  {
    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.getUsername())

        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() +1000 * 60 * 24))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)

        .compact();

  }

  public boolean isTokenWalid(String token, UserDetails userDetails){
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }
  private Date extractExpiration(String token) {
   return extractClaim(token, Claims::getExpiration);
  }


  public <T>T extractClaim(String token, Function<Claims, T> claimResolver ){
    final Claims claims = extractAllClaims(token);
    return claimResolver.apply(claims);
  }


  public Claims extractAllClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build().parseClaimsJws(token)
        .getBody();
  }


  private Key getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes); //create cryptographic key
  }
}
