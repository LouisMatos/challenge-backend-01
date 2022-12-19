package br.com.luis.challengebackend.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.luis.challengebackend.model.User;
import br.com.luis.challengebackend.properties.TokenProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class TokenService {

	@Autowired
	private TokenProperties properties;

	public String generateToken(Authentication authentication) {
		User logged = (User) authentication.getPrincipal();
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime expiration = today.plus(properties.getExpirationTime(), ChronoUnit.MILLIS);

		return Jwts.builder().setIssuer("API da Aluraflix").setSubject(logged.getId().toString())
				.setIssuedAt(toDate(today)).setExpiration(toDate(expiration))
				.signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, properties.getSecretKey()).compact();
	}

	private Date toDate(LocalDateTime date) {
		return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
	}

	public boolean isValidToken(String token) {
		try {
			Jwts.parser().setSigningKey(properties.getSecretKey()).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getUserId(String token) {
		Claims claims = Jwts.parser().setSigningKey(properties.getSecretKey()).parseClaimsJws(token).getBody();
		String userId = claims.getSubject();
		return Long.parseLong(userId);
	}

}
