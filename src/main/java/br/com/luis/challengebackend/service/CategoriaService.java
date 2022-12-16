package br.com.luis.challengebackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.luis.challengebackend.dto.CategoriaRequestDTO;
import br.com.luis.challengebackend.dto.CategoriaResponseDTO;
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
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private VideoRepository videoRepository;

	public List<CategoriaResponseDTO> listarTodasCategorias(Pageable pageable) {

		Page<Categoria> categorias = categoriaRepository.findAll(pageable);

		if (categorias.getContent().isEmpty()) {
			throw new NotFoundException("Não há categorias na pagina: " + categorias.getNumber());
		}

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
			log.warn("Já existe uma categoria cadastrada com o mesmo Titulo! - {} ", categoriaRequestDTO.getTitulo());
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

	public List<VideoResponseDTO> buscarVideosPorCategoria(Long id, Pageable pageable) {

		Optional<Categoria> categoria = categoriaRepository.findById(id);

		List<VideoResponseDTO> videosResponses = new ArrayList<>();

		if (categoria.isPresent()) {
			Page<Video> videos = videoRepository.findAllByCategoriaId(categoria.get(), pageable);
			for (Video video : videos) {
				VideoResponseDTO videoResponseDTO = new VideoResponseDTO();
				videosResponses.add(videoResponseDTO.convert(video));
			}
		} else {
			throw new UnprocessableEntityException("Não existe categoria com o id: " + id);
		}

		if (categoria.get().getVideos().isEmpty()) {
			throw new NotFoundException("Não há videos cadastrados para essa categoria!");
		}

		return videosResponses;
	}

}
