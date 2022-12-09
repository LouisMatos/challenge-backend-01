package br.com.luis.challengebackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.luis.challengebackend.dto.CategoriaRequestDTO;
import br.com.luis.challengebackend.dto.CategoriaResponseDTO;
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

	public List<CategoriaResponseDTO> listarTodasCategorias() {

		List<Categoria> categorias = categoriaRepository.findAll();

		if (categorias.isEmpty()) {
			throw new NotFoundException("Não há categorias cadastrados!");
		}

		List<CategoriaResponseDTO> dtos = new ArrayList<>();

		for (Categoria categoria : categorias) {
			dtos.add(new CategoriaResponseDTO().convert(categoria));
		}

		return dtos;
	}

	public CategoriaResponseDTO buscarCategoriaPorId(Long id) {
		return new CategoriaResponseDTO().convert(categoriaRepository.findById(id).orElseThrow(
				() -> new NotFoundException("Nenhuma categoria foi encontrada com o id informado: " + id)));
	}

	public CategoriaResponseDTO salvarCategoria(@Valid CategoriaRequestDTO categoriaRequestDTO) {

		boolean exists = categoriaRepository.existsByTitulo(categoriaRequestDTO.getTitulo());

		if (exists) {
			throw new UnprocessableEntityException("Já existe uma categoria cadastrada com o mesmo Titulo!");
		}

		Categoria categoria = new Categoria();
		categoria.setCor(categoriaRequestDTO.getCor());
		categoria.setTitulo(categoriaRequestDTO.getTitulo());

		return new CategoriaResponseDTO().convert(categoriaRepository.save(categoria));
	}

	public void deletarCategoria(Long id) {
		boolean exists = categoriaRepository.existsById(id);
		if (!exists) {
			throw new UnprocessableEntityException("Não existe categoria com o id: " + id);
		}
		categoriaRepository.deleteById(id);
	}

	public CategoriaResponseDTO alterarCategoria(Long id, @Valid CategoriaRequestDTO categoriaRequestDTO) {
		boolean exists = categoriaRepository.existsById(id);
		if (!exists) {
			throw new UnprocessableEntityException("Não existe categoria com o id: " + id);
		}

		Categoria categoria = new Categoria();
		categoria.setCor(categoriaRequestDTO.getCor());
		categoria.setTitulo(categoriaRequestDTO.getTitulo());
		categoria.setId(id);

		return new CategoriaResponseDTO().convert(categoriaRepository.save(categoria));
	}

	public List<VideoResponseDTO> buscarVideosPorCategoria(Long id) {

		Optional<Categoria> categoria = categoriaRepository.findById(id);

		if (categoria.isEmpty()) {
			throw new UnprocessableEntityException("Não existe categoria com o id: " + id);
		}

		if (categoria.get().getVideos().isEmpty()) {
			throw new NotFoundException("Não há videos cadastrados para essa categoria!");
		}

		List<Video> videos = categoria.get().getVideos();

		List<VideoResponseDTO> videosResponses = new ArrayList<>();

		for (Video video : videos) {
			VideoResponseDTO videoResponseDTO = new VideoResponseDTO();
			videosResponses.add(videoResponseDTO.convert(video));
		}

		return videosResponses;
	}

}
