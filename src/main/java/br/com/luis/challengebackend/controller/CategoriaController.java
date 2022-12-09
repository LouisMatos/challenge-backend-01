package br.com.luis.challengebackend.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luis.challengebackend.dto.CategoriaRequestDTO;
import br.com.luis.challengebackend.dto.CategoriaResponseDTO;
import br.com.luis.challengebackend.dto.VideoResponseDTO;
import br.com.luis.challengebackend.service.CategoriaService;
import io.swagger.annotations.Api;

@Api("Api de Videos")
@CrossOrigin
@RestController
@RequestMapping("/categorias")
@Validated
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@PostMapping
	public ResponseEntity<CategoriaResponseDTO> cadastrarCategoria(@Valid @RequestBody CategoriaRequestDTO categoriaRequestDTO) {
		return ResponseEntity.ok().body(categoriaService.salvarCategoria(categoriaRequestDTO));
	}

	@GetMapping
	public ResponseEntity<List<CategoriaResponseDTO>> listarTodasCategorias() {
		return ResponseEntity.ok().body(categoriaService.listarTodasCategorias());
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoriaResponseDTO> buscarCategoriaPorId(@PathVariable("id") Long id) {
		return ResponseEntity.ok().body(categoriaService.buscarCategoriaPorId(id));
	}

	@GetMapping("/{id}/videos")
	public ResponseEntity<List<VideoResponseDTO>> buscarVideosPorCategoria(@PathVariable("id") Long id) {
		return ResponseEntity.ok().body(categoriaService.buscarVideosPorCategoria(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarCategoria(@PathVariable("id") Long id) {
		categoriaService.deletarCategoria(id);
		return ResponseEntity.accepted().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoriaResponseDTO> alterarCategoria(@PathVariable("id") Long id,
			@Valid @RequestBody CategoriaRequestDTO categoriaRequestDTO) {
		return ResponseEntity.ok().body(categoriaService.alterarCategoria(id, categoriaRequestDTO));
	}

}
