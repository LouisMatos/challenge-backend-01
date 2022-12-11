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
		executor.setCorePoolSize(1500);
		executor.setMaxPoolSize(2000);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("CarThread-");
		executor.setKeepAliveSeconds(29);
		executor.setAwaitTerminationSeconds(29);
		executor.initialize();
		return executor;
	}

}
