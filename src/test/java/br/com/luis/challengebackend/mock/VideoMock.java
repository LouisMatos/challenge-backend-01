package br.com.luis.challengebackend.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import br.com.luis.challengebackend.dto.VideoRequestDTO;
import br.com.luis.challengebackend.dto.VideoResponseDTO;
import br.com.luis.challengebackend.model.Video;

public class VideoMock {

	public static final VideoRequestDTO VIDEO = carregarVideo();

	public static final Video VIDEO_SERVICE = carregaVideoService();

	public static final Optional<Video> VIDEO_SERVICE_OP = carregaVideoServiceOP();

	public static final Page<Video> VIDEO_SERVICE_FIND_ALL_OP = carregaVideoServiceFindAllOP();

	public static final Page<Video> VIDEO_SERVICE_FIND_ALL_NOT_FOUND = carregaVideoServiceFindAllNotFound();

	public static final VideoResponseDTO VIDEO_RESPONSE = carregaVideoResponseDTO();

	public static final List<VideoResponseDTO> FIND_ALL = carregarListarTodos();

	public static final VideoRequestDTO VIDEO_BAD_REQUEST = carregarVideoBadRequest();

	public static final VideoRequestDTO VIDEO_WITHOUT_CATEGORY = carregarVideoSemCategoria();

	public static final String VIDEO_RESPONSE_BAD_REQUEST = "{\"violations\":[{\"campo\":\"titulo\",\"mensagem\":\"Campo titulo é obrigatório!\"},{\"campo\":\"titulo\",\"mensagem\":\"Titulo deve ter tamanho minimo de 2 caracteres\"},{\"campo\":\"url\",\"mensagem\":\"Url deve ter tamanho minimo de 2 caracteres\"},{\"campo\":\"url\",\"mensagem\":\"Campo url é obrigatório!\"}]}";

	public static final String VIDEO_FIND_ALL_NOT_FOUND = "{\"timestamp\":1670272584333,\"status\":404,\"error\":\"NOT_FOUND\",\"message\":\"Não há videos cadastrados!\",\"path\":\"/videos\"}";

	public static final String VIDEO_NOT_FOUND = "{\"timestamp\":1670267562653,\"status\":404,\"error\":\"NOT_FOUND\",\"message\":\"Nenhum video foi encontrado com o id informado: 1\",\"path\":\"/videos/1\"}";

	public static final String VIDEO_UNPROCESSABLE_ENTITY_DELETE = "{\"timestamp\":1670271691983,\"status\":422,\"error\":\"UNPROCESSABLE_ENTITY\",\"message\":\"Não existe video com o id: 1\",\"path\":\"/videos/1\"}";

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

	private static Video carregaVideoService() {
		return Video.builder().id(1L).descricao("Teste").titulo("Teste").url("https://teste.com.br")
				.categoriaId(CategoriaMock.CATEGORIA_SERVICE.get()).build();
	}

	private static Optional<Video> carregaVideoServiceOP() {
		return Optional.of(Video.builder().id(1L).descricao("Teste").titulo("Teste").url("https://teste.com.br")
				.categoriaId(CategoriaMock.CATEGORIA_SERVICE.get()).build());
	}

	private static Page<Video> carregaVideoServiceFindAllOP() {

		List<Video> videos = new ArrayList<>();

		videos.add(Video.builder().id(1L).descricao("Teste").titulo("Teste").url("https://teste.com.br")
				.categoriaId(CategoriaMock.CATEGORIA_SERVICE.get()).build());

		Page<Video> page = new PageImpl<>(videos);

		return page;
	}

	private static Page<Video> carregaVideoServiceFindAllNotFound() {
		List<Video> videos = new ArrayList<>();
		Page<Video> page = new PageImpl<>(videos);
		return page;
	}

}
