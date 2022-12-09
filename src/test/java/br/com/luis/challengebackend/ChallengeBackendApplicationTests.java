package br.com.luis.challengebackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureMockMvc
@SpringBootTest(classes = ChallengeBackendApplicationTests.class)
class ChallengeBackendApplicationTests {

	@Test
	void contextLoads() {
		ChallengeBackendApplication.main(new String[] {});
	}

}
