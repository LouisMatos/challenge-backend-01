package br.com.luis.challengebackend.mock;

import br.com.luis.challengebackend.model.Categoria;

public class CategoriaMock {

	public static final Categoria CATEGORIA = carregaCategoria();

	private static Categoria carregaCategoria() {

		return Categoria.builder().id(1L).cor("#cbd1ff").titulo("LIVRE").build();

	}

}
