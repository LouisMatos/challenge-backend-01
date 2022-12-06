package br.com.luis.challengebackend.mock;

import br.com.luis.challengebackend.dto.CategoriaRequestDTO;
import br.com.luis.challengebackend.dto.CategoriaResponseDTO;

public class CategoriaMock {

	public static final CategoriaRequestDTO CATEGORIA = carregaCategoria();

	public static final CategoriaResponseDTO CATEGORIA_RESPONSE = carregaCategoriaResponse();

	public static final Object CATEGORIA_BAD_REQUEST = carregarCategoriaBadRequest();

	public static final String CATEGORIA_RESPONSE_BAD_REQUEST = "{\"violations\":[{\"campo\":\"cor\",\"mensagem\":\"Cor deve ter tamanho minimo de 2 caracteres\"},{\"campo\":\"cor\",\"mensagem\":\"Campo Cor é obrigatório!\"},{\"campo\":\"titulo\",\"mensagem\":\"Titulo deve ter tamanho minimo de 2 caracteres\"},{\"campo\":\"titulo\",\"mensagem\":\"Campo titulo é obrigatório!\"}]}";

	public static final String CATEGORIA_UNPROCESSABLE_ENTITY_POST = "{\"timestamp\":1670283933938,\"status\":422,\"error\":\"UNPROCESSABLE_ENTITY\",\"message\":\"Já existe uma categoria cadastrada com o mesmo Titulo!\",\"path\":\"/categorias\"}";

	private static CategoriaRequestDTO carregaCategoria() {
		return CategoriaRequestDTO.builder().cor("#cbd1ff").titulo("LIVRE").build();
	}

	private static CategoriaResponseDTO carregaCategoriaResponse() {
		return CategoriaResponseDTO.builder().id(1L).cor("#c185510").titulo("LIVRE").build();
	}

	private static CategoriaRequestDTO carregarCategoriaBadRequest() {
		return CategoriaRequestDTO.builder().cor("").titulo("").build();
	}

}
