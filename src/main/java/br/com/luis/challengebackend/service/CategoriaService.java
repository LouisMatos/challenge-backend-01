package br.com.luis.challengebackend.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.luis.challengebackend.dto.CategoriaDTO;
import br.com.luis.challengebackend.dto.VideoResponseDTO;
import br.com.luis.challengebackend.exception.NotFoundException;
import br.com.luis.challengebackend.exception.UnprocessableEntityException;
import br.com.luis.challengebackend.model.Categoria;
import br.com.luis.challengebackend.model.Video;
import br.com.luis.challengebackend.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ModelMapper mapper;

	public List<Categoria> listarTodasCategorias() {

		if (categoriaRepository.findAll().isEmpty()) {
			throw new NotFoundException("Não há categorias cadastrados!");
		}

		return categoriaRepository.findAll();
	}

	public Categoria buscarCategoriaPorId(Long id) {
		return categoriaRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Nenhuma categoria foi encontrada com o id informado: " + id));
	}

	public Categoria salvarCategoria(@Valid CategoriaDTO categoriaDTO) {
		boolean exists = categoriaRepository.existsByTitulo(categoriaDTO.getTitulo());
		if (exists) {
			throw new UnprocessableEntityException("Já existe uma categoria cadastrada com o mesmo Titulo!");
		}

		Categoria categoria = mapper.map(categoriaDTO, Categoria.class);
		return categoriaRepository.save(categoria);
	}

	public void deletarCategoria(Long id) {
		boolean exists = categoriaRepository.existsById(id);
		if (!exists) {
			throw new UnprocessableEntityException("Não existe categoria com o id: " + id);
		}
		categoriaRepository.deleteById(id);
	}

	public Categoria alterarCategoria(Long id, @Valid CategoriaDTO categoriaDTO) {
		boolean exists = categoriaRepository.existsById(id);
		if (!exists) {
			throw new UnprocessableEntityException("Não existe categoria com o id: " + id);
		}

		Categoria categoria = mapper.map(categoriaDTO, Categoria.class);
		categoria.setId(id);

		return categoriaRepository.save(categoria);
	}

	public List<VideoResponseDTO> buscarVideosPorCategoria(Long id) {
		boolean exists = categoriaRepository.existsById(id);
		if (!exists) {
			throw new UnprocessableEntityException("Não existe categoria com o id: " + id);
		}

		List<Video> videos = categoriaRepository.findById(id).get().getVideos();

		if (videos.isEmpty()) {
			throw new NotFoundException("Não há videos cadastrados para essa categoria!");
		}
		List<VideoResponseDTO> videosResponses = new ArrayList<>();

		for (Video video : videos) {
			VideoResponseDTO videoResponseDTO = new VideoResponseDTO();
			videosResponses.add(videoResponseDTO.convert(video));
		}

		return videosResponses;
	}

}
