package br.com.luis.challengebackend.controller;

import java.util.concurrent.CompletableFuture;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luis.challengebackend.dto.TokenDto;
import br.com.luis.challengebackend.model.Login;
import br.com.luis.challengebackend.service.AuthService;

@RestController
@RequestMapping(value = "auth")
@Profile("prod")
public class AuthenticationController {

	@Autowired
	private  AuthService authService;

	@Autowired
	private AuthenticationManager authManager;

	@PostMapping
	@Async(value = "taskExecutor")
	public CompletableFuture<ResponseEntity<TokenDto>> authenticate(@RequestBody @Valid Login form) {
		CompletableFuture<TokenDto> tokenDto = authService.authenticate(form, this.authManager);
		return tokenDto.thenApply(t -> ResponseEntity.ok().body(t));
	}

}
