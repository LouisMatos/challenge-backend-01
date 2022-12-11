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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import br.com.luis.challengebackend.dto.CategoriaResponseDTO;
import br.com.luis.challengebackend.dto.VideoResponseDTO;
import br.com.luis.challengebackend.exception.NotFoundException;
import br.com.luis.challengebackend.exception.UnprocessableEntityException;
import br.com.luis.challengebackend.mock.CategoriaMock;
import br.com.luis.challengebackend.repository.CategoriaRepository;
import br.com.luis.challengebackend.repository.VideoRepository;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoriaServiceTest {

	@InjectMocks
	private CategoriaService categoriaService;

	@Mock
	private VideoRepository videoRepository;

	@Mock
	private CategoriaRepository categoriaRepository;

	@Test
	void returnCreateCategoria() {

		when(categoriaRepository.existsByTitulo(anyString())).thenReturn(false);

		when(categoriaRepository.save(any())).thenReturn(CategoriaMock.CATEGORIA_SERVICE.get());

		CategoriaResponseDTO response = categoriaService.salvarCategoria(CategoriaMock.CATEGORIA);

		assertNotNull(response);

		verify(categoriaRepository).existsByTitulo(anyString());

		verify(categoriaRepository).save(any());
	}

	@Test
	void returnAlreadyCreatedCategoria() {

		when(categoriaRepository.existsByTitulo(anyString())).thenReturn(true);

		when(categoriaRepository.save(any())).thenReturn(CategoriaMock.CATEGORIA_SERVICE.get());

		UnprocessableEntityException thrown = assertThrows(UnprocessableEntityException.class,
				() -> categoriaService.salvarCategoria(CategoriaMock.CATEGORIA));

		assertEquals(thrown.getMessage(), ("Já existe uma categoria cadastrada com o mesmo Titulo!"));
	}

	@Test
	void returnFindAllCategorias() {

		Pageable pageable = PageRequest.of(0, 5);

		when(categoriaRepository.findAll(pageable)).thenReturn(CategoriaMock.CATEGORIA_SERVICE_FIND_ALL);

		List<CategoriaResponseDTO> response = categoriaService.listarTodasCategorias(pageable);

		assertNotNull(response);

		verify(categoriaRepository).findAll(pageable);

	}

	@Test
	void returnFindAllNotFoundCategoria() {

		Pageable pageable = PageRequest.of(0, 5);

		when(categoriaRepository.findAll(pageable)).thenReturn(CategoriaMock.CATEGORIA_SERVICE_FIND_ALL_NOT_FOUND);

		NotFoundException thrown = assertThrows(NotFoundException.class,
				() -> categoriaService.listarTodasCategorias(pageable));

		assertEquals(thrown.getMessage(), ("Não há categorias na pagina: " + 0));

	}

	@Test
	void returnCategoriaFindById() {

		when(categoriaRepository.findById(anyLong())).thenReturn(CategoriaMock.CATEGORIA_SERVICE);

		CategoriaResponseDTO response = categoriaService.buscarCategoriaPorId(anyLong());

		assertNotNull(response);

		verify(categoriaRepository).findById(anyLong());

	}

	@Test
	void returnCategoriaNotFoundFindById() {

		Long id = 1L;

		NotFoundException thrown = assertThrows(NotFoundException.class,
				() -> categoriaService.buscarCategoriaPorId(id));

		assertEquals(thrown.getMessage(), ("Nenhuma categoria foi encontrada com o id informado: " + id));

	}

	@Test
	void deleteCategoria() {

		Long id = 1l;

		when(categoriaRepository.existsById(anyLong())).thenReturn(true);

		categoriaService.deletarCategoria(id);

		verify(categoriaRepository).deleteById(id);

	}

	@Test
	void returnVideoNotFoundDelete() {

		Long id = 1l;

		when(categoriaRepository.existsById(anyLong())).thenReturn(false);

		UnprocessableEntityException thrown = assertThrows(UnprocessableEntityException.class,
				() -> categoriaService.deletarCategoria(id));

		assertEquals(thrown.getMessage(), ("Não existe categoria com o id: " + id));

	}

	@Test
	void returnUpdateCategoria() {

		Long id = 1L;

		when(categoriaRepository.existsById(id)).thenReturn(true);

		when(categoriaRepository.save(any())).thenReturn(CategoriaMock.CATEGORIA_SERVICE.get());

		CategoriaResponseDTO response = categoriaService.alterarCategoria(id, CategoriaMock.CATEGORIA);

		assertNotNull(response);

		verify(categoriaRepository).existsById(id);

		verify(categoriaRepository).save(any());

	}

	@Test
	void returnUpdateCategoriaNotFound() {

		Long id = 1L;

		when(categoriaRepository.existsById(id)).thenReturn(false);

		UnprocessableEntityException thrown = assertThrows(UnprocessableEntityException.class,
				() -> categoriaService.alterarCategoria(id, CategoriaMock.CATEGORIA));

		assertEquals(thrown.getMessage(), ("Não existe categoria com o id: " + id));

	}

	@Test
	void returnVideoPorCategoria() {

		Long id = 1L;

		when(categoriaRepository.findById(id)).thenReturn(CategoriaMock.VIDEO_CATEGORIA_SERVICE);

		List<VideoResponseDTO> response = categoriaService.buscarVideosPorCategoria(id);

		assertNotNull(response);

		verify(categoriaRepository).findById(id);

	}

	@Test
	void returnNotFoudCategoriaFindVideoPorCategoria() {

		Long id = 1L;

		when(categoriaRepository.existsById(id)).thenReturn(false);

		UnprocessableEntityException thrown = assertThrows(UnprocessableEntityException.class,
				() -> categoriaService.buscarVideosPorCategoria(id));

		assertEquals(thrown.getMessage(), ("Não existe categoria com o id: " + id));

	}

	@Test
	void returnNotFoudVideoFindVideoPorCategoria() {

		Long id = 1L;

		when(categoriaRepository.existsById(id)).thenReturn(true);

		when(categoriaRepository.findById(id)).thenReturn(CategoriaMock.CATEGORIA_SERVICE);

		NotFoundException thrown = assertThrows(NotFoundException.class,
				() -> categoriaService.buscarVideosPorCategoria(id));

		assertEquals(thrown.getMessage(), ("Não há videos cadastrados para essa categoria!"));

	}

}
