package br.com.luis.challengebackend.dto;

import br.com.luis.challengebackend.model.Video;
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
public class VideoResponseDTO {

	private Long id;

	private Long categoriaId;

	private String descricao;

	private String titulo;

	private String url;

	public VideoResponseDTO convert(Video video) {
		this.setId(video.getId());
		this.setCategoriaId(video.getCategoriaId().getId());
		this.setDescricao(video.getDescricao());
		this.setTitulo(video.getTitulo());
		this.setUrl(video.getUrl());
		return this;
	}
}
