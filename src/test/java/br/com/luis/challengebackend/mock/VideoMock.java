package br.com.luis.challengebackend.mock;

import java.util.ArrayList;
import java.util.List;

import br.com.luis.challengebackend.dto.VideoRequestDTO;
import br.com.luis.challengebackend.dto.VideoResponseDTO;

public class VideoMock {

	public static final VideoRequestDTO VIDEO = carregarVideo();

	public static final VideoResponseDTO VIDEO_RESPONSE = carregaVideoResponseDTO();

	public static final List<VideoResponseDTO> FIND_ALL = carregarListarTodos();

	public static final VideoRequestDTO VIDEO_BAD_REQUEST = carregarVideoBadRequest();

	public static final VideoRequestDTO VIDEO_WITHOUT_CATEGORY = carregarVideoSemCategoria();

	public static final String VIDEO_RESPONSE_BAD_REQUEST = "{\"violations\":[{\"campo\":\"titulo\",\"mensagem\":\"Titulo deve ter tamanho minimo de 2 caracteres\"},{\"campo\":\"url\",\"mensagem\":\"Url deve ter tamanho minimo de 2 caracteres\"},{\"campo\":\"url\",\"mensagem\":\"Campo url é obrigatório!\"},{\"campo\":\"titulo\",\"mensagem\":\"Campo titulo é obrigatório!\"}]}";

	private static VideoResponseDTO carregaVideoResponseDTO() {

		return VideoResponseDTO.builder().id(1L).titulo("Teste").categoriaId(1L).descricao("Teste")
				.url("https://www.teste.com.br").build();
	}

	private static VideoRequestDTO carregarVideo() {

		return VideoRequestDTO.builder().titulo("Teste").descricao("Teste").url("http://www.teste.com.br")
				.categoriaId(1L).build();
	}

	private static VideoRequestDTO carregarVideoSemCategoria() {

		return VideoRequestDTO.builder().titulo("Teste").descricao("Teste").url("http://www.teste.com.br").build();
	}

	private static VideoRequestDTO carregarVideoBadRequest() {

		return VideoRequestDTO.builder().titulo("").descricao("").url("").categoriaId(1L).build();
	}

	private static List<VideoResponseDTO> carregarListarTodos() {

		List<VideoResponseDTO> dtos = new ArrayList<>();

		dtos.add(VideoResponseDTO.builder().id(1L).categoriaId(1L).descricao("Teste").url("http://www.teste.com.br")
				.titulo("teste").build());

		return dtos;

	}

}