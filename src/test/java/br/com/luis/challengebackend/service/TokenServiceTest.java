package br.com.luis.challengebackend.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import br.com.luis.challengebackend.mock.UserMock;
import br.com.luis.challengebackend.properties.TokenProperties;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TokenServiceTest {

	@InjectMocks
	private TokenService tokenService;

	@MockBean
	private Authentication authentication;

	@Mock
	private TokenProperties properties;

	@Test
	void returnTokenGenerated() {

		when(properties.getExpirationTime()).thenReturn(1800000L);
		when(properties.getSecretKey()).thenReturn(
				"knlb<#&hL%)r()WO(A|RaOr3[3^f(hdS.%t)raQ3GAM?-iF2jE+9TddL:Zq&6-|s0tcI-t#[t?(fZ,|8[-jLO$XMD%]90]TQnwaZ");

		when(authentication.getPrincipal()).thenReturn(UserMock.USER.get());

		String token = tokenService.generateToken(authentication);

		assertNotNull(token);

		assertTrue(tokenService.isValidToken(token));

	}

	@Test
	void returnTokenNotValid() {

		assertFalse(tokenService.isValidToken("123852"));

	}

	@Test
	void returnUserId() {

		when(properties.getExpirationTime()).thenReturn(1800000L);
		when(properties.getSecretKey()).thenReturn(
				"knlb<#&hL%)r()WO(A|RaOr3[3^f(hdS.%t)raQ3GAM?-iF2jE+9TddL:Zq&6-|s0tcI-t#[t?(fZ,|8[-jLO$XMD%]90]TQnwaZ");

		when(authentication.getPrincipal()).thenReturn(UserMock.USER.get());

		String token = tokenService.generateToken(authentication);

		Long id = tokenService.getUserId(token);

		assertEquals(1L, id);

	}

}
