package br.com.luis.challengebackend.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {

	private String secretKey = "rzxlszyykpbgqcflzxsqcysyhljt";

	private long validityInMs = 1800000; // 30m

}