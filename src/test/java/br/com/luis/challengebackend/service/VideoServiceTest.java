package br.com.luis.challengebackend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import br.com.luis.challengebackend.dto.VideoResponseDTO;
import br.com.luis.challengebackend.exception.NotFoundException;
import br.com.luis.challengebackend.exception.UnprocessableEntityException;
import br.com.luis.challengebackend.mock.CategoriaMock;
import br.com.luis.challengebackend.mock.VideoMock;
import br.com.luis.challengebackend.repository.CategoriaRepository;
import br.com.luis.challengebackend.repository.VideoRepository;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VideoServiceTest {

	@InjectMocks
	private VideoService videoService;

	@Mock
	private VideoRepository videoRepository;

	@Mock
	private CategoriaRepository categoriaRepository;

	@Test
	void returnCreateVideo() {

		when(videoRepository.save(any())).thenReturn(VideoMock.VIDEO_SERVICE);

		when(videoRepository.existsByUrl(anyString())).thenReturn(false);

		when(categoriaRepository.findById(anyLong())).thenReturn(CategoriaMock.CATEGORIA_SERVICE);

		VideoResponseDTO response = videoService.salvarVideo(VideoMock.VIDEO);

		assertNotNull(response);

		verify(videoRepository).existsByUrl(any());

		verify(videoRepository).save(any());

	}

	@Test
	void returnAlreadyCreatedVideo() {

		when(videoRepository.save(any())).thenReturn(VideoMock.VIDEO_SERVICE);

		when(videoRepository.existsByUrl(anyString())).thenReturn(true);

		UnprocessableEntityException thrown = assertThrows(UnprocessableEntityException.class,
				() -> videoService.salvarVideo(VideoMock.VIDEO));

		assertEquals(thrown.getMessage(), ("Já existe um video cadastrado com a mesma url!"));
	}

	@Test
	void returnCreateVideoWhithoutCategoria() {

		when(videoRepository.save(any())).thenReturn(VideoMock.VIDEO_SERVICE);

		when(videoRepository.existsByUrl(anyString())).thenReturn(false);

		when(categoriaRepository.findById(anyLong())).thenReturn(CategoriaMock.CATEGORIA_SERVICE);

		VideoResponseDTO response = videoService.salvarVideo(VideoMock.VIDEO_WITHOUT_CATEGORY);

		assertNotNull(response);

		verify(videoRepository).existsByUrl(any());

		verify(videoRepository).save(any());
	}

	@Test
	void returnVideoFindyById() {

		Long id = 1L;

		when(videoRepository.findById(anyLong())).thenReturn(VideoMock.VIDEO_SERVICE_OP);

		VideoResponseDTO response = videoService.buscarVideoPorId(id);

		assertNotNull(response);

		verify(videoRepository).findById(anyLong());
	}

	@Test
	void returnVideoNotFound() {

		Long id = 1L;

		NotFoundException thrown = assertThrows(NotFoundException.class, () -> videoService.buscarVideoPorId(id));

		assertEquals(thrown.getMessage(), ("Nenhum video foi encontrado com o id informado: " + id));

	}

	@Test
	void videoDelete() {
		Long id = 1L;

		when(videoRepository.existsById(anyLong())).thenReturn(true);

		videoService.deletarVideo(id);

		verify(videoRepository).deleteById(id);
	}

	@Test
	void returnVideoNotFoundDelete() {
		Long id = 1L;

		when(videoRepository.existsById(anyLong())).thenReturn(false);

		UnprocessableEntityException thrown = assertThrows(UnprocessableEntityException.class,
				() -> videoService.deletarVideo(id));

		assertEquals(thrown.getMessage(), ("Não existe video com o id: " + id));
	}

	@Test
	void returnVideoAlterado() {

		Long id = 1L;

		when(videoRepository.save(any())).thenReturn(VideoMock.VIDEO_SERVICE);

		when(videoRepository.existsById(anyLong())).thenReturn(true);

		when(categoriaRepository.findById(anyLong())).thenReturn(CategoriaMock.CATEGORIA_SERVICE);

		VideoResponseDTO response = videoService.alterarVideo(id, VideoMock.VIDEO);

		assertNotNull(response);

		verify(videoRepository).existsById(anyLong());

		verify(videoRepository).save(any());

	}

	@Test
	void returnVideoNotFoundAlterado() {
		Long id = 1L;

		when(videoRepository.existsById(anyLong())).thenReturn(false);

		UnprocessableEntityException thrown = assertThrows(UnprocessableEntityException.class,
				() -> videoService.alterarVideo(id, VideoMock.VIDEO));

		assertEquals(thrown.getMessage(), ("Não existe video com o id: " + id));
	}

	@Test
	void returnListAllVideosWithoutSearch() {

		when(videoRepository.findAll()).thenReturn(VideoMock.VIDEO_SERVICE_FIND_ALL_OP);

		List<VideoResponseDTO> videos = videoService.listarTodosVideos(null);

		assertNotNull(videos);

		verify(videoRepository).findAll();
	}

	@Test
	void returnNotFoundListVideosWithoutSearch() {

		when(videoRepository.findAll()).thenReturn(VideoMock.VIDEO_SERVICE_FIND_ALL_NOT_FOUND);

		NotFoundException thrown = assertThrows(NotFoundException.class, () -> videoService.listarTodosVideos(null));

		assertEquals(thrown.getMessage(), ("Não há videos cadastrados!"));

	}

	@Test
	void returnListAllVideosWithSearch() {

		when(videoRepository.findByTituloContains(anyString())).thenReturn(VideoMock.VIDEO_SERVICE_FIND_ALL_OP);

		List<VideoResponseDTO> videos = videoService.listarTodosVideos(anyString());

		assertNotNull(videos);

		verify(videoRepository).findByTituloContains(anyString());
	}

	@Test
	void returnNotFoundListVideosWithSearch() {

		String titulo = "Teste";

		when(videoRepository.findByTituloContains(titulo)).thenReturn(VideoMock.VIDEO_SERVICE_FIND_ALL_NOT_FOUND);

		NotFoundException thrown = assertThrows(NotFoundException.class, () -> videoService.listarTodosVideos(titulo));

		assertEquals(thrown.getMessage(), ("Nenhum video foi encontrado com o titulo informado: " + titulo));
	}

}
