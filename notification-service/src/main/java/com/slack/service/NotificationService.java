
package com.slack.service;
import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slack.model.Token;
import com.slack.model.User;
import com.slack.repository.TokenRepository;
import com.slack.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@Service
public class NotificationService {
	@Autowired
	private TokenRepository tokenRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private TokenNextSequenceService tokenNextSequenceService;
	public String createToken(int userId, String userMail, long ttlInMilliSeconds)
	{
		Token token = new Token();
		token.setUserId(userId);
		String tokenString = createJWT(userId,userMail,ttlInMilliSeconds);
		token.setTokenString(tokenString.toString());
		token.setTimeout((int) ttlInMilliSeconds);
		token.setTokenId(this.tokenNextSequenceService.getNextSequence("tokenCustomSequences"));
		tokenRepository.save(token);
		
		return tokenString;
	}
	private static String SECRET_KEY = "oeRaYY7Wo24sDqKSX3IM9ASGmdGPmkTd9jo1QTy4b7P9Ze5_9hKolVX8xNrQDcNRfVEdTZNOuOyqEGhXEbdJI-ZQ19k_o9MI0y3eZN2lp9jow55FfXMiINEdt1XR85VipRLSOkT6kSpzs2x-jbLDiz9iFVzkd81YKxMgPA7VfZeQUm4n-mOmnWMaVX30zGFU4L3oPBctYKkl4dYfqYWqRNfrgPJVi5DGFjywgxx0ASEiJHtV72paI3fDR2XwlSkyhhmY-ICjCRmsJN4fX1pdoL8a18-aQrvyu4j0Os6dVPYIoPvvY0SAZtWYKHfM15g7A3HD4cVREf9cUsprCRK93w";
	public static String createJWT(int id, String issuerMail, long ttlMillis) {
		  
	    //The JWT signature algorithm we will be using to sign the token
	    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	    long nowMillis = System.currentTimeMillis();
	    Date now = new Date(nowMillis);

	    //We will sign our JWT with our ApiKey secret
	    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
	    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

	    //Let's set the JWT Claims
	    JwtBuilder builder = Jwts.builder().setId(Integer.toString(id))
	            .setIssuedAt(now)
	            .setIssuer(issuerMail)
	            .signWith(signatureAlgorithm, signingKey);
	  
	    //if it has been specified, let's add the expiration
	    if (ttlMillis > 0) {
	        long expMillis = nowMillis + ttlMillis;
	        Date exp = new Date(expMillis);
	        builder.setExpiration(exp);
	    }  
	  
	    //Builds the JWT and serializes it to a compact, URL-safe string
	    return builder.compact();
	}
	public static Claims decodeJWT(String jwt) {
	    //This line will throw an exception if it is not a signed JWS (as expected)
	    Claims claims = Jwts.parser()
	            .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
	            .parseClaimsJws(jwt).getBody();
	   System.out.println(claims);
	    return claims;
	}

	public User getUserByUserMail(String email)
	{
		return userRepository.findByUserMail(email);
	}


	

}
