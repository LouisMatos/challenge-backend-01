package br.com.luis.challengebackend;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureMockMvc
@SuppressWarnings("static-access")
@SpringBootTest(classes = ChallengeBackendApplicationTests.class)
class ChallengeBackendApplicationTests {

	@Test
	void contextLoads() {

		ChallengeBackendApplication app = new ChallengeBackendApplication();

		app.main(new String[] {});

		assertNotNull(app);
	}

}
