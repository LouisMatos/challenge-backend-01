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

import br.com.luis.challengebackend.dto.CategoriaDTO;
import br.com.luis.challengebackend.dto.VideoResponseDTO;
import br.com.luis.challengebackend.model.Categoria;
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

	@GetMapping
	public ResponseEntity<List<Categoria>> listarTodasCategorias() {
		return ResponseEntity.ok().body(categoriaService.listarTodasCategorias());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Categoria> buscarCategoriaPorId(@PathVariable("id") Long id) {
		return ResponseEntity.ok().body(categoriaService.buscarCategoriaPorId(id));
	}

	@PostMapping
	public ResponseEntity<Categoria> cadastrarCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {
		return ResponseEntity.ok().body(categoriaService.salvarCategoria(categoriaDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarCategoria(@PathVariable("id") Long id) {
		categoriaService.deletarCategoria(id);
		return ResponseEntity.accepted().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Categoria> alterarCategoria(@PathVariable("id") Long id,
			@Valid @RequestBody CategoriaDTO categoriaDTO) {
		return ResponseEntity.ok().body(categoriaService.alterarCategoria(id, categoriaDTO));
	}

	@GetMapping("/{id}/videos")
	public ResponseEntity<List<VideoResponseDTO>> buscarVideosPorCategoria(@PathVariable("id") Long id){
		return ResponseEntity.ok().body(categoriaService.buscarVideosPorCategoria(id));
	}

}
