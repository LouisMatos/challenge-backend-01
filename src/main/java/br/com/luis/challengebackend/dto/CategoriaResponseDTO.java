package br.com.luis.challengebackend.dto;

import br.com.luis.challengebackend.model.Categoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaResponseDTO {

	private Long id;

	private String cor;

	private String titulo;

	public CategoriaResponseDTO convert(Categoria categoria) {
		this.setId(categoria.getId());
		this.setCor(categoria.getCor());
		this.setTitulo(categoria.getTitulo());
		return this;
	}

}
