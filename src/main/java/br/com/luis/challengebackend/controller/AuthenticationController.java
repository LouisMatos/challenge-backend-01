package br.com.luis.challengebackend.controller;
import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luis.challengebackend.dto.AuthenticationRequestDTO;
import br.com.luis.challengebackend.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.var;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationManager authenticationManager;

	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/signin")
	public ResponseEntity signin(@RequestBody AuthenticationRequestDTO data) {

		try {
			String username = data.getUsername();

			var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));

			String token = jwtTokenProvider.createToken(authentication);

			Map<Object, Object> model = new HashMap<>();
			model.put("username", username);
			model.put("token", token);
			model.put("expire_in", jwtTokenProvider.expireIn(token));
			return ok(model);
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password supplied");
		}
	}
}