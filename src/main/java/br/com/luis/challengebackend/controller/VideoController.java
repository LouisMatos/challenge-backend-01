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

import br.com.luis.challengebackend.dto.VideoDTO;
import br.com.luis.challengebackend.model.Video;
import br.com.luis.challengebackend.service.VideoService;
import io.swagger.annotations.Api;

@Api("Api de equipes")
@CrossOrigin
@RestController
@RequestMapping("/videos")
@Validated
public class VideoController {

	@Autowired
	private VideoService videoService;

	@GetMapping
	public ResponseEntity<List<Video>> listarCompromissos() {
		return ResponseEntity.ok().body(videoService.listarTodosVideos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Video> buscarVideoPorId(@PathVariable("id") Long id) {
		return ResponseEntity.ok().body(videoService.buscarVideoPorId(id));
	}

	@PostMapping
	public ResponseEntity<Video> cadastrarVideo(@Valid @RequestBody VideoDTO videoDTO) {
		return ResponseEntity.ok().body(videoService.salvarVideo(videoDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarVideo(@PathVariable("id") Long id) {
		videoService.deletarvideo(id);
		return ResponseEntity.accepted().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Video> atualizarVideo(@PathVariable("id") Long id, @Valid @RequestBody VideoDTO videoDTO) {
		return ResponseEntity.ok().body(videoService.alterarVideo(id, videoDTO));
	}

}
