package br.com.luis.challengebackend.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.luis.challengebackend.util.JwtProperties;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public JwtProperties jwtPropertiess() {
		return new JwtProperties();
	}

}
