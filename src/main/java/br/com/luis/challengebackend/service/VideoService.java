package br.com.luis.challengebackend.service;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.luis.challengebackend.dto.VideoDTO;
import br.com.luis.challengebackend.exception.NotFoundException;
import br.com.luis.challengebackend.exception.UnprocessableEntityException;
import br.com.luis.challengebackend.model.Video;
import br.com.luis.challengebackend.repository.VideoRepository;

@Service
public class VideoService {

	@Autowired
	private VideoRepository videoRepository;

	@Autowired
	private ModelMapper mapper;

	public List<Video> listarTodosVideos() {

		if (videoRepository.findAll().isEmpty()) {
			throw new NotFoundException("Não há videos cadastrados!");
		}

		return videoRepository.findAll();
	}

	public Video buscarVideoPorId(Long id) {
		return videoRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nenhum video foi encontrado com o id informado: " + id));
	}

	public Video salvarVideo(@Valid VideoDTO videoDTO) {
		boolean exists = videoRepository.existsByUrl(videoDTO.getUrl());
		if (exists) {
			throw new UnprocessableEntityException("Já existe um video cadastrado com a mesma url!");
		}

		Video video = mapper.map(videoDTO, Video.class);
		return videoRepository.save(video);

	}

	public void deletarvideo(Long id) {
		boolean exists = videoRepository.existsById(id);
		if (!exists) {
			throw new UnprocessableEntityException("Não existe video com o id: " + id);
		}
		videoRepository.deleteById(id);
	}

	public Video alterarVideo(Long id, @Valid VideoDTO videoDTO) {
		boolean exists = videoRepository.existsById(id);
		if (!exists) {
			throw new UnprocessableEntityException("Não existe video com o id: " + id);
		}

		Video video = mapper.map(videoDTO, Video.class);
		video.setId(id);

		return videoRepository.save(video);
	}

}
