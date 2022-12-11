package br.com.luis.challengebackend.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import br.com.luis.challengebackend.dto.CategoriaRequestDTO;
import br.com.luis.challengebackend.dto.CategoriaResponseDTO;
import br.com.luis.challengebackend.dto.VideoResponseDTO;
import br.com.luis.challengebackend.model.Categoria;
import br.com.luis.challengebackend.model.Video;

public class CategoriaMock {

	public static final CategoriaRequestDTO CATEGORIA = carregaCategoria();

	public static final Optional<Categoria> CATEGORIA_SERVICE = carregaCategoriaService();

	public static final List<Categoria> CATEGORIA_SERVICE_FIND_ALL = carregaCategoriaServiceFindAll();

	public static final List<Categoria> CATEGORIA_SERVICE_FIND_ALL_NOT_FOUND = carregaCategoriaServiceFindAllNotFound();

	public static final CategoriaResponseDTO CATEGORIA_RESPONSE = carregaCategoriaResponse();

	public static final CategoriaRequestDTO CATEGORIA_BAD_REQUEST = carregarCategoriaBadRequest();

	public static final List<CategoriaResponseDTO> FIND_ALL = carregarListarCategorias();

	public static final List<VideoResponseDTO> FIND_ALL_CATEGORIA_VIDEO = carregarListarTodosVideosCategoria();

	public static final String CATEGORIA_RESPONSE_BAD_REQUEST = "{\"violations\":[{\"campo\":\"titulo\",\"mensagem\":\"Titulo deve ter tamanho minimo de 2 caracteres\"},{\"campo\":\"cor\",\"mensagem\":\"Cor deve ter tamanho minimo de 2 caracteres\"},{\"campo\":\"cor\",\"mensagem\":\"Campo Cor é obrigatório!\"},{\"campo\":\"titulo\",\"mensagem\":\"Campo titulo é obrigatório!\"}]}";

	public static final String CATEGORIA_UNPROCESSABLE_ENTITY_POST = "{\"timestamp\":1670283933938,\"status\":422,\"error\":\"UNPROCESSABLE_ENTITY\",\"message\":\"Já existe uma categoria cadastrada com o mesmo Titulo!\",\"path\":\"/categorias\"}";

	public static final String CATEGORIA_NOT_FOUND = "{\"timestamp\":1670267562653,\"status\":404,\"error\":\"NOT_FOUND\",\"message\":\"Nenhuma categoria foi encontrada com o id informado: 1\",\"path\":\"/categorias/1\"}";

	public static final String CATEGORIA_FIND_ALL_NOT_FOUND = "{\"timestamp\":1670272584333,\"status\":404,\"error\":\"NOT_FOUND\",\"message\":\"Não há categorias cadastrados!\",\"path\":\"/categorias\"}";

	public static final String CATEGORIA_UNPROCESSABLE_ENTITY = "{\"timestamp\":1670271691983,\"status\":422,\"error\":\"UNPROCESSABLE_ENTITY\",\"message\":\"Não existe categoria com o id: 1\",\"path\":\"/categorias/1\"}";

	public static final String CATEGORIA_UNPROCESSABLE_ENTITY_VIDEO = "{\"timestamp\":1670271691983,\"status\":422,\"error\":\"UNPROCESSABLE_ENTITY\",\"message\":\"Não existe categoria com o id: 1\",\"path\":\"/categorias/1/videos\"}";

	public static final String CATEGORIA_FIND_ALL_NOT_FOUND_VIDEO = "{\"timestamp\":1670369316113,\"status\":404,\"error\":\"NOT_FOUND\",\"message\":\"Não há videos cadastrados para essa categoria!\",\"path\":\"/categorias/1/videos\"}";

	public static final Optional<Categoria> VIDEO_CATEGORIA_SERVICE = carregaVideoPorCategoria();

	public static final Pageable PAGE = carregarPage();

	private static CategoriaRequestDTO carregaCategoria() {
		return CategoriaRequestDTO.builder().cor("#cbd1ff").titulo("LIVRE").build();
	}

	private static Pageable carregarPage() {

		return Pageable.unpaged();

	}

	private static List<CategoriaResponseDTO> carregarListarCategorias() {
		List<CategoriaResponseDTO> dtos = new ArrayList<>();

		dtos.add(CategoriaResponseDTO.builder().id(1L).titulo("LIVRE").cor("#ccc123").build());

		return dtos;
	}

	private static CategoriaResponseDTO carregaCategoriaResponse() {
		return CategoriaResponseDTO.builder().id(1L).cor("#c185510").titulo("LIVRE").build();
	}

	private static CategoriaRequestDTO carregarCategoriaBadRequest() {
		return CategoriaRequestDTO.builder().cor("").titulo("").build();
	}

	private static List<VideoResponseDTO> carregarListarTodosVideosCategoria() {

		List<VideoResponseDTO> dtos = new ArrayList<>();

		dtos.add(VideoResponseDTO.builder().id(1L).categoriaId(1L).descricao("Teste").url("http://www.teste.com.br")
				.titulo("teste").build());

		return dtos;

	}

	private static Optional<Categoria> carregaCategoriaService() {
		Categoria categoria = new Categoria();
		categoria.setCor("#5fasf");
		categoria.setTitulo("LIVRE");
		categoria.setId(1L);
		return Optional.of(categoria);
	}

	private static List<Categoria> carregaCategoriaServiceFindAll() {

		List<Categoria> categorias = new ArrayList<>();

		categorias.add(Categoria.builder().cor("#ac1234").titulo("LIVRE").id(1L).build());

		return categorias;
	}

	private static List<Categoria> carregaCategoriaServiceFindAllNotFound() {

		List<Categoria> categorias = new ArrayList<>();

		return categorias;

	}

	private static Optional<Categoria> carregaVideoPorCategoria() {
		List<Video> videos = new ArrayList<>();

		videos.add(Video.builder().id(1L).categoriaId(Categoria.builder().id(1L).titulo("LIVRE").cor("3fdd21").build())
				.titulo("Teste").url("https://www.teste.com.br").descricao("teste").build());

		Categoria categoria = new Categoria();
		categoria.setCor("#5fasf");
		categoria.setTitulo("LIVRE");
		categoria.setId(1L);
		categoria.setVideos(videos);
		return Optional.of(categoria);
	}

	// private static List<Video> carregaVideoPorCategoria() {
	//
	// List<Video> videos = new ArrayList<>();
	//
	// videos.add(Video.builder().id(1L)
	// .titulo("Teste").url("https://www.teste.com.br").descricao("teste").build());
	//
	// return videos;
	// }

}
