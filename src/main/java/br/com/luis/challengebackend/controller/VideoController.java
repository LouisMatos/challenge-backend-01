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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.luis.challengebackend.dto.VideoRequestDTO;
import br.com.luis.challengebackend.dto.VideoResponseDTO;
import br.com.luis.challengebackend.service.VideoService;
import io.swagger.annotations.Api;

@Api("Api de Videos")
@CrossOrigin
@RestController
@RequestMapping("/videos")
@Validated
public class VideoController {

	@Autowired
	private VideoService videoService;

	@GetMapping
	public ResponseEntity<List<VideoResponseDTO>> listarCompromissos(@RequestParam(required = false, name = "search") String titulo) {
		return ResponseEntity.ok().body( videoService.listarTodosVideos(titulo));
	}

	@GetMapping("/{id}")
	public ResponseEntity<VideoResponseDTO> buscarVideoPorId(@PathVariable("id") Long id) {
		return ResponseEntity.ok().body(videoService.buscarVideoPorId(id));
	}

	@PostMapping
	public ResponseEntity<VideoResponseDTO> cadastrarVideo(@Valid @RequestBody VideoRequestDTO videoRequestDTO) {
		return ResponseEntity.ok().body(videoService.salvarVideo(videoRequestDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarVideo(@PathVariable("id") Long id) {
		videoService.deletarVideo(id);
		return ResponseEntity.accepted().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<VideoResponseDTO> atualizarVideo(@PathVariable("id") Long id, @Valid @RequestBody VideoRequestDTO videoRequestDTO) {
		return ResponseEntity.ok().body(videoService.alterarVideo(id, videoRequestDTO));
	}

}
