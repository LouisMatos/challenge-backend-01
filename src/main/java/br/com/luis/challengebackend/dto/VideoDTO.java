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
public class VideoDTO {

	@Size(min = 2, message = "Titulo deve ter tamanho minimo de 2 caracteres")
	@Size(max = 80, message = "Titulo deve ter tamanho maximo de 80 caracteres")
	@NotBlank(message = "Campo titulo é obrigatório!")
	private String titulo;

	@Size(min = 2, message = "Descrição deve ter tamanho minimo de 2 caracteres")
	@Size(max = 250, message = "Descrição deve ter tamanho maximo de 250 caracteres")
	@NotBlank(message = "Campo descrição é obrigatório!")
	private String descricao;

	@Size(min = 2, message = "Url deve ter tamanho minimo de 2 caracteres")
	@Size(max = 250, message = "Url deve ter tamanho maximo de 250 caracteres")
	@NotBlank(message = "Campo url é obrigatório!")
	private String url;

}
