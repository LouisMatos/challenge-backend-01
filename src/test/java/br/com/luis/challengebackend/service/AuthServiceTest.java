package br.com.luis.challengebackend.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import br.com.luis.challengebackend.dto.TokenDto;
import br.com.luis.challengebackend.model.Login;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthServiceTest {

	@InjectMocks
	private AuthService authService;

	@Mock
	private TokenService tokenService;

	@Mock
	private AuthenticationManager authManager;

	@Test
	void returnToken() {

		Login login = Login.builder().email("teste@teste.com.br").password("123456789").build();

		when(tokenService.generateToken(any())).thenReturn(
				"eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBUEkgZGEgQWx1cmFmbGl4Iiwic3ViIjoiMiIsImlhdCI6MTY3MTM3ODUxNSwiZXhwIjoxNjcxMzgwMzE1fQ.pEkeUbU-EFkAGag_2BmZhKHSDfxtdb6cswu1JKTecMI");

		TokenDto dto = authService.authenticate(login, authManager);

		assertNotNull(dto.getToken());
		assertNotNull(dto.getType());

		verify(tokenService).generateToken(any());

	}

	@Test
	void returnTokenInvalid() {

		Login login = Login.builder().email("teste@teste.com.br").password("123456789").build();

		doThrow(UsernameNotFoundException.class).when(authManager).authenticate(any());

		UsernameNotFoundException thrown = assertThrows(UsernameNotFoundException.class,
				() -> authService.authenticate(login, authManager));

		assertEquals(("Credenciais inválidas"), thrown.getMessage());
	}

}
