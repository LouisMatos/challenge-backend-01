package br.com.luis.challengebackend.config;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(AsyncConfig.class);

	@Bean(name = "taskExecutor")
	public Executor taskExecutor() {
		LOGGER.debug("Creating Async Task Executor");
		final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(250);
		executor.setMaxPoolSize(500);
		executor.setQueueCapacity(250);
		executor.setThreadNamePrefix("ChallengeThread-");
		executor.setKeepAliveSeconds(60);
		executor.initialize();
		return executor;
	}

}
