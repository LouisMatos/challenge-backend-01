package br.com.luis.challengebackend.mock;

import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import br.com.luis.challengebackend.dto.TokenDto;
import br.com.luis.challengebackend.model.Login;
import br.com.luis.challengebackend.model.User;

public class UserMock {

	public static final Optional<User> USER = carregaUsuario();

	public static final Optional<User> USER_NOT_FOUND = carregaUsuarioVazio();

	public static final UsernamePasswordAuthenticationToken USER_AUTHENTICATION = carregarUsuarioAuthentication();

	public static final Login LOGIN = carregaLogin();

	//	public static final Login LOGIN2 = carregaLogin2();

	public static final TokenDto TOKEN = carregaToken();

	private static Optional<User> carregaUsuario() {

		return Optional.of(User.builder().id(1L).email("teste@teste.com.br").name("teste")
				.password("$2a$10$zVZezD0Vm4z/xRSFTkoXVO4MWWss9VgX/5HxXOSoxBqdfhMnJmnyW").build());
	}

	private static TokenDto carregaToken() {
		return new TokenDto("eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBUEkgZGEgQWx1cmFmbGl4Iiwic3ViIjoiMiIsImlhdCI6MTY3MTM3ODUxNSwiZXhwIjoxNjcxMzgwMzE1fQ.pEkeUbU-EFkAGag_2BmZhKHSDfxtdb6cswu1JKTecMI", "Bearer");
	}

	private static Login carregaLogin() {
		return new Login("teste@teste.com.br", "password");
	}

	private static UsernamePasswordAuthenticationToken carregarUsuarioAuthentication() {

		Login form = new Login("teste@teste.com.br", "$2a$10$zVZezD0Vm4z/xRSFTkoXVO4MWWss9VgX/5HxXOSoxBqdfhMnJmnyW");

		return form.converter();
	}

	private static Optional<User> carregaUsuarioVazio() {

		Optional.of(User.builder().build());
		return Optional.empty();
	}
}
