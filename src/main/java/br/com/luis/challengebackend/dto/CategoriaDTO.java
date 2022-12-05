package br.com.luis.challengebackend.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {

	@Size(min = 2, message = "Cor deve ter tamanho minimo de 2 caracteres")
	@Size(max = 80, message = "Cor deve ter tamanho maximo de 15 caracteres")
	@NotBlank(message = "Campo Cor é obrigatório!")
	private String cor;

	@Size(min = 2, message = "Titulo deve ter tamanho minimo de 2 caracteres")
	@Size(max = 80, message = "Titulo deve ter tamanho maximo de 30 caracteres")
	@NotBlank(message = "Campo titulo é obrigatório!")
	private String titulo;

}
