package br.com.luis.challengebackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.luis.challengebackend.dto.VideoRequestDTO;
import br.com.luis.challengebackend.dto.VideoResponseDTO;
import br.com.luis.challengebackend.exception.NotFoundException;
import br.com.luis.challengebackend.exception.UnprocessableEntityException;
import br.com.luis.challengebackend.model.Categoria;
import br.com.luis.challengebackend.model.Video;
import br.com.luis.challengebackend.repository.CategoriaRepository;
import br.com.luis.challengebackend.repository.VideoRepository;

@Service
public class VideoService {

	@Autowired
	private VideoRepository videoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	public List<VideoResponseDTO> listarTodosVideos(String titulo) {

		List<VideoResponseDTO> videosResponses = new ArrayList<>();

		if (videoRepository.findAll().isEmpty()) {
			throw new NotFoundException("Não há videos cadastrados!");
		}

		List<Video> videos;

		if (titulo == null) {

			videos = videoRepository.findAll();

		} else {

			if (videoRepository.findByTituloContains(titulo) == null) {
				throw new NotFoundException("Nenhum video foi encontrado com o titulo informado: " + titulo);
			}

			videos = videoRepository.findByTituloContains(titulo);

		}

		for (Video video : videos) {
			videosResponses.add(new VideoResponseDTO().convert(video));
		}

		return videosResponses;
	}

	public VideoResponseDTO buscarVideoPorId(Long id) {
		return new VideoResponseDTO().convert(videoRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nenhum video foi encontrado com o id informado: " + id)));
	}

	public VideoResponseDTO salvarVideo(@Valid VideoRequestDTO videoRequestDTO) {

		boolean exists = videoRepository.existsByUrl(videoRequestDTO.getUrl());

		if (exists) {
			throw new UnprocessableEntityException("Já existe um video cadastrado com a mesma url!");
		}

		return salvaEAtualizaVideo(videoRequestDTO, null);

	}

	public void deletarVideo(Long id) {
		boolean exists = videoRepository.existsById(id);
		if (!exists) {
			throw new UnprocessableEntityException("Não existe video com o id: " + id);
		}
		videoRepository.deleteById(id);
	}

	public VideoResponseDTO alterarVideo(Long id, @Valid VideoRequestDTO videoRequestDTO) {
		boolean exists = videoRepository.existsById(id);
		if (!exists) {
			throw new UnprocessableEntityException("Não existe video com o id: " + id);
		}

		return salvaEAtualizaVideo(videoRequestDTO, id);
	}

	private VideoResponseDTO salvaEAtualizaVideo(VideoRequestDTO videoRequestDTO, Long id) {
		Video video = new Video();
		Optional<Categoria> categoria;

		if (videoRequestDTO.getCategoriaId() == null) {
			categoria = categoriaRepository.findById(Long.parseLong("1"));
		} else {
			categoria = categoriaRepository.findById(videoRequestDTO.getCategoriaId());
		}

		video.setDescricao(videoRequestDTO.getDescricao());
		video.setTitulo(videoRequestDTO.getTitulo());
		video.setUrl(videoRequestDTO.getUrl());
		video.setCategoriaId(categoria.get());
		video.setId(id);

		return new VideoResponseDTO().convert(videoRepository.save(video));
	}

}
