package br.com.luis.challengebackend.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class TokenProperties {

	@Value("${aluraflix.jwt.secret}")
	private String secretKey;

	@Value("${aluraflix.jwt.expiration}")
	private long expirationTime;

}
