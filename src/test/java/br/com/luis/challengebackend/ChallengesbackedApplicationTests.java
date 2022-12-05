package br.com.luis.challengebackend;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureMockMvc
@SpringBootTest(classes=ChallengesbackedApplicationTests.class)
class ChallengesbackedApplicationTests {

	@Test
	void contextLoads() {
		assertTrue(true);
	}

}
