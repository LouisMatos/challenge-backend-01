package br.com.luis.challengebackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.luis.challengebackend.config.TokenService;
import br.com.luis.challengebackend.dto.TokenDto;
import br.com.luis.challengebackend.model.Login;

@Service
@Profile("prod")
public class AuthService {

	@Autowired
	private TokenService tokenService;

	public TokenDto authenticate(Login form, AuthenticationManager authManager) {

		UsernamePasswordAuthenticationToken login = form.converter();

		try {
			Authentication authentication = authManager.authenticate(login);
			String token = tokenService.generateToken(authentication);
			return new TokenDto(token, "Bearer");

		} catch (AuthenticationException e) {
			throw new UsernameNotFoundException("Credenciais inválidas");
		}
	}

}
