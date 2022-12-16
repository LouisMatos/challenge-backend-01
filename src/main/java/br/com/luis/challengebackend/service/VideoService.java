package br.com.luis.challengebackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.luis.challengebackend.dto.VideoRequestDTO;
import br.com.luis.challengebackend.dto.VideoResponseDTO;
import br.com.luis.challengebackend.exception.NotFoundException;
import br.com.luis.challengebackend.exception.UnprocessableEntityException;
import br.com.luis.challengebackend.model.Categoria;
import br.com.luis.challengebackend.model.Video;
import br.com.luis.challengebackend.repository.CategoriaRepository;
import br.com.luis.challengebackend.repository.VideoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VideoService {

	@Autowired
	private VideoRepository videoRepository;

	@Autowired
	private CategoriaRepository categoriaRepository;

	public VideoResponseDTO salvarVideo(@Valid VideoRequestDTO videoRequestDTO) {

		boolean exists = videoRepository.existsByUrl(videoRequestDTO.getUrl());

		if (exists) {
			log.warn("Já existe um video cadastrado com a mesma url!");
			throw new UnprocessableEntityException("Já existe um video cadastrado com a mesma url!");
		}

		log.info("Processando video para salvar no banco!");
		return salvaEAtualizaVideo(videoRequestDTO, null);

	}

	public List<VideoResponseDTO> listarTodosVideos(String titulo, Pageable pageable) {

		List<VideoResponseDTO> videosResponses = new ArrayList<>();

		Page<Video> videos = null;

		if (titulo == null) {

			videos = videoRepository.findAll(pageable);

			if (videos.getContent().isEmpty()) {
				throw new NotFoundException("Não há videos na pagina: " + videos.getNumber());
			}

			if (videos.isEmpty()) {
				throw new NotFoundException("Não há videos cadastrados!");
			}

		} else {

			videos = videoRepository.findByTituloContains(titulo, pageable);

			if (videos.getContent().isEmpty()) {
				throw new NotFoundException("Não há videos na pagina: " + videos.getNumber());
			}

			if (videos.isEmpty()) {
				throw new NotFoundException("Nenhum video foi encontrado com o titulo informado: " + titulo);
			}

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
			log.info("Video sem categoria!");
			log.info("Adcionando categoria Livre ao video!");
			categoria = categoriaRepository.findById(Long.parseLong("1"));
		} else {
			log.info("Video com categoria!");
			categoria = categoriaRepository.findById(videoRequestDTO.getCategoriaId());
		}

		video.setDescricao(videoRequestDTO.getDescricao());
		video.setTitulo(videoRequestDTO.getTitulo());
		video.setUrl(videoRequestDTO.getUrl());
		video.setCategoriaId(categoria.get());
		video.setId(id);

		log.info("Salvando novo videno no banco de dados!");
		return new VideoResponseDTO().convert(videoRepository.save(video));
	}

	public List<VideoResponseDTO> free() {
		List<VideoResponseDTO> videosResponses = new ArrayList<>();

		List<Video> videos = null;

		videos = videoRepository.findByFree();

		if (videos.isEmpty()) {
			throw new NotFoundException("Não há videos cadastrados!");
		}

		for (Video video : videos) {
			videosResponses.add(new VideoResponseDTO().convert(video));
		}

		return videosResponses;
	}

}
