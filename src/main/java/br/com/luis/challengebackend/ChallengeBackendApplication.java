package br.com.luis.challengebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableJpaRepositories
@SpringBootApplication
public class ChallengeBackendApplication {

	public static void main(String[] args) {
		final SpringApplication app = new SpringApplication(ChallengeBackendApplication.class);
		app.addListeners(new ApplicationPidFileWriter("challenge-backend.pid"));
		app.run(args);
		// SpringApplication.run(ChallengeBackendApplication.class, args);
	}

}
