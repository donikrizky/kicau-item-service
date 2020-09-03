package com.donikrizky.kicau.itemservice.security;

import java.security.SignatureException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donikrizky.kicau.itemservice.config.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class TokenProvider {
	private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

	@Autowired
	private JwtConfig jwtConfig;

	public Integer getUserIdFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token).getBody();
		return Integer.parseInt(claims.getSubject());
	}

	public Date getLogoutTimeFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token).getBody();

		return claims.get("LogoutTime", Date.class);
	}

	public String getUsernameFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(token).getBody();

		return claims.get("LogoutTime", String.class);
	}
	
	public boolean validateToken(String authToken, Date logoutTime) throws SignatureException {
		try {
			Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecret()).parseClaimsJws(authToken).getBody();
			if (logoutTime == null || claims.getIssuedAt().after(logoutTime)) {
				return true;
			}
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
		}
		return false;
	}
}
