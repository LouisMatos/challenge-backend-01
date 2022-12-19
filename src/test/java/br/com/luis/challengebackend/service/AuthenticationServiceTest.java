package br.com.luis.challengebackend.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import br.com.luis.challengebackend.mock.UserMock;
import br.com.luis.challengebackend.repository.UserRepository;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationServiceTest {

	@InjectMocks
	private AuthenticationService authenticationService;

	@Mock
	private UserRepository userRepository;

	@Test
	void returnLoadUserByEmail() {

		String username = "teste@teste.com.br";

		when(userRepository.findByEmail(username)).thenReturn(UserMock.USER);

		UserDetails userDetails = authenticationService.loadUserByUsername(username);

		assertNotNull(userDetails);

		verify(userRepository).findByEmail(username);

	}

	@Test
	void returnNotFoundLoadUserByEmail() {

		String username = "teste@teste.com.br";

		when(userRepository.findByEmail(username)).thenReturn(UserMock.USER_NOT_FOUND);

		UsernameNotFoundException thrown = assertThrows(UsernameNotFoundException.class,
				() -> authenticationService.loadUserByUsername(username));

		assertEquals(("Usuário Inexistente!"), thrown.getMessage());
	}

}
