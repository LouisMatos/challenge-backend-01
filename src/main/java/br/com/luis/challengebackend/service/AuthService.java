package br.com.luis.challengebackend.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.luis.challengebackend.dto.TokenDto;
import br.com.luis.challengebackend.model.Login;
import lombok.extern.slf4j.Slf4j;

@Service
@Profile("prod")
@Slf4j
public class AuthService {

	@Autowired
	private TokenService tokenService;

	@Async
	public CompletableFuture<TokenDto> authenticate(Login form, AuthenticationManager authManager) {

		UsernamePasswordAuthenticationToken login = form.converter();

		try {
			Authentication authentication = authManager.authenticate(login);
			String token = tokenService.generateToken(authentication);
			log.info("Crio o token: {}", token);
			return CompletableFuture.completedFuture(new TokenDto(token, "Bearer"));

		} catch (AuthenticationException e) {
			throw new UsernameNotFoundException("Credenciais inválidas");
		}
	}

	public TokenDto authenticate2(Login form, AuthenticationManager authManager) {

		UsernamePasswordAuthenticationToken login = form.converter();

		try {
			Authentication authentication = authManager.authenticate(login);
			log.info("Usuário autenticado!");
			String token = tokenService.generateToken(authentication);
			log.info("Crio o token: {}", token);
			return new TokenDto(token, "Bearer");

		} catch (AuthenticationException e) {
			throw new UsernameNotFoundException("Credenciais inválidas");
		}
	}

}
