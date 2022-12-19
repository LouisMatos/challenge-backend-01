package br.com.luis.challengebackend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.google.gson.Gson;

import br.com.luis.challengebackend.mock.UserMock;
import br.com.luis.challengebackend.service.AuthService;

@AutoConfigureMockMvc
@ActiveProfiles("prod")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthService authService;

	@MockBean
	private AuthenticationManager authManager;

	@Test
	void return200WhenAuthenticate() throws Exception {

		when(authService.authenticate(any(), any())).thenReturn(UserMock.TOKEN);

		MvcResult result = mockMvc.perform( //
				post("/auth") //
				.contentType(APPLICATION_JSON) //
				.content(toString(UserMock.LOGIN))) //
				.andExpect(status().isOk()) //
				.andReturn();

		JSONAssert.assertEquals(toString(UserMock.TOKEN),
				result.getResponse().getContentAsString(StandardCharsets.UTF_8), JSONCompareMode.NON_EXTENSIBLE);
	}

	private String toString(final Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}

}
