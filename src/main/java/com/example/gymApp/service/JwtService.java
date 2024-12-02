package com.example.gymApp.service;


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


/*
JwtService performs the key tasks of JWT processing: extracting tokens and verifying the signature using a secret key.
The secret key is decoded and used to verify the token.
Once verified, you can extract specific data from the token as JSON and transform it into the desired objects.
 */
@Service
public class JwtService {
//
//  private static final String SECRET_KEY =
//      "PL+rgtYceM//T3nOWL4hl4Fa2lblR31Sse/PnUqCOyA=\n";

//  @Value("${jwt.secret-key}");
//  private final String secretKey;

  @Value("${jwt.secret-key}")
  private String secretKey;

//  public JwtService(@Value("${jwt.secret-key}") String secretKey) {
//    this.secretKey = secretKey;
//  }


//3.
  //get username from token
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

// 5. the token on the single userDetails base - will use it later
String generateToken(UserDetails userDetails){
    return generateToken(new HashMap<>(), userDetails);
  }





  //4. generate token from extraClaims + userdetails
  public String generateToken(
      Map<String, Object> extraClaims, //additional information (markers) that can be added to the token.
      // For example, this could be user data, roles, or other parameters that you want to include in the JWT.

      UserDetails userDetails  //is an object representing a user, typically containing the username,
      // password, and roles. In this case, it is used to extract the username that is included in the token.
  )
  {
    return Jwts.builder() //initializes builder for creating jwt token
        .setClaims(extraClaims) //add additional claims/ This method adds claims that are passed through the extraClaims argument
        .setSubject(userDetails.getUsername()) // The "subject" field indicates who the token belongs to, most often the username

        .setIssuedAt(new Date(System.currentTimeMillis())) //This method specifies when the token was generated
        .setExpiration(new Date(System.currentTimeMillis() +1000 * 60 * 24)) //sets the token expiration time. - 24 minutes
        .signWith(getSigningKey(), SignatureAlgorithm.HS256) // method creates a digital signature for the token using the secret key
                                                             //The token signature is used to verify its authenticity.

        .compact(); //finish/ This method collects all the parts of the token (header, payload and signature)
                    // and compiles them into a single string, which is the final form of the JWT token.

  }

// this 3 methods work together
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


  //2.
  //The method allows extracting specific data (claim) from a JWT token using a universal approach.
  public <T>T extractClaim(String token, Function<Claims, T> claimResolver ){
    final Claims claims = extractAllClaims(token); //get claims from jwt
    return claimResolver.apply(claims); // transform claim to specific meaning
  }

//1.
  public Claims extractAllClaims(String token) {
    return Jwts.parserBuilder() //parse token
        .setSigningKey(getSigningKey()) //use key
        .build().parseClaimsJws(token)  //parses the token using the specified key for validation.
        .getBody(); //returns the token contents as a Claims object,
    // which contains all the token data
    // (e.g. username, issuedAt, expiration, and other fields).
  }


  //This method decodes SECRET_KEY from Base64 format to a byte array and creates a key for HMAC SHA-256 signing
  private Key getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes); //create cryptographic key
  }
}
