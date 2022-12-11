package br.com.luis.challengebackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.ValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.google.gson.Gson;

import br.com.luis.challengebackend.exception.NotFoundException;
import br.com.luis.challengebackend.exception.UnprocessableEntityException;
import br.com.luis.challengebackend.mock.CategoriaMock;
import br.com.luis.challengebackend.service.CategoriaService;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoriaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CategoriaService categoriaService;

	@Test
	void return200WhenSaveNewCategoria() throws Exception {

		when(categoriaService.salvarCategoria(any())).thenReturn(CategoriaMock.CATEGORIA_RESPONSE);

		MvcResult result = mockMvc.perform( //
				post("/categorias") //
				.contentType(APPLICATION_JSON) //
				.content(toString(CategoriaMock.CATEGORIA))) //
				.andExpect(status().isOk()) //
				.andReturn();

		JSONAssert.assertEquals(toString(CategoriaMock.CATEGORIA_RESPONSE),
				result.getResponse().getContentAsString(StandardCharsets.UTF_8), JSONCompareMode.NON_EXTENSIBLE);

	}

	@Test
	void return400WhenSaveNewCategoriaWithoutFields() throws Exception {

		MvcResult result = mockMvc.perform( //
				post("/categorias") //
				.contentType(APPLICATION_JSON) //
				.content(toString(CategoriaMock.CATEGORIA_BAD_REQUEST))) //
				.andExpect(status().isBadRequest()) //
				.andReturn();

		JSONAssert.assertEquals(CategoriaMock.CATEGORIA_RESPONSE_BAD_REQUEST,
				result.getResponse().getContentAsString(StandardCharsets.UTF_8), JSONCompareMode.NON_EXTENSIBLE);
	}

	@Test
	void return422WhenAlreadyCategoriaSave() throws Exception {

		when(categoriaService.salvarCategoria(any()))
		.thenThrow(new UnprocessableEntityException("Já existe uma categoria cadastrada com o mesmo Titulo!"));

		MvcResult result = mockMvc.perform( //
				post("/categorias") //
				.contentType(APPLICATION_JSON) //
				.content(toString(CategoriaMock.CATEGORIA))) //
				.andExpect(status().isUnprocessableEntity()) //
				.andReturn();

		JSONAssert.assertEquals(CategoriaMock.CATEGORIA_UNPROCESSABLE_ENTITY_POST,
				result.getResponse().getContentAsString(StandardCharsets.UTF_8), new CustomComparator(
						JSONCompareMode.STRICT, Customization.customization("timestamp", new ValueMatcher<Object>() {
							@Override
							public boolean equal(Object o1, Object o2) {
								return true;
							}
						})));

	}

	@Test
	void return200WhenFindByIdCategoria() throws Exception {

		Long id = 1L;

		when(categoriaService.buscarCategoriaPorId(anyLong())).thenReturn(CategoriaMock.CATEGORIA_RESPONSE);

		MvcResult result = mockMvc.perform( //
				get("/categorias/" + id) //
				.contentType(APPLICATION_JSON)) //
				.andExpect(status().isOk()) //
				.andReturn();

		assertEquals(toString(CategoriaMock.CATEGORIA_RESPONSE), result.getResponse().getContentAsString());

	}

	@Test
	void return404WhenSearchCategoriaNotFound() throws Exception {

		Long id = 1L;

		when(categoriaService.buscarCategoriaPorId(any()))
		.thenThrow(new NotFoundException("Nenhuma categoria foi encontrada com o id informado: " + id));

		MvcResult result = mockMvc.perform( //
				get("/categorias/" + id) //
				.contentType(APPLICATION_JSON)) //
				.andExpect(status().isNotFound()) //
				.andReturn();

		JSONAssert.assertEquals(CategoriaMock.CATEGORIA_NOT_FOUND,
				result.getResponse().getContentAsString(StandardCharsets.UTF_8), new CustomComparator(
						JSONCompareMode.STRICT, Customization.customization("timestamp", new ValueMatcher<Object>() {
							@Override
							public boolean equal(Object o1, Object o2) {
								return true;
							}
						})));
	}

	@Test
	void return200FindAllCategorias() throws Exception {

		when(categoriaService.listarTodasCategorias(any())).thenReturn(CategoriaMock.FIND_ALL);

		MvcResult result = mockMvc.perform( //
				get("/categorias?page=0") //
				.contentType(APPLICATION_JSON)) //
				.andExpect(status().isOk()).andReturn();

		assertEquals(toString(CategoriaMock.FIND_ALL), result.getResponse().getContentAsString());

	}

	@Test
	void return404WhenFindAllCategoriasNotFound() throws Exception {

		when(categoriaService.listarTodasCategorias(any()))
		.thenThrow(new NotFoundException("Não há categorias cadastrados!"));

		MvcResult result = mockMvc.perform( //
				get("/categorias?page=0") //
				.contentType(APPLICATION_JSON)) //
				.andExpect(status().isNotFound()).andReturn();

		JSONAssert.assertEquals(CategoriaMock.CATEGORIA_FIND_ALL_NOT_FOUND,
				result.getResponse().getContentAsString(StandardCharsets.UTF_8), new CustomComparator(
						JSONCompareMode.STRICT, Customization.customization("timestamp", new ValueMatcher<Object>() {
							@Override
							public boolean equal(Object o1, Object o2) {
								return true;
							}
						})));

	}

	@Test
	void return202WhenDeleteCategoria() throws Exception {

		mockMvc.perform( //
				delete("/categorias/1") //
				.contentType(APPLICATION_JSON)) //
		.andExpect(status().isAccepted());

	}

	@Test
	void return422WhenDeleteCategoriaNotFound() throws Exception {

		Long id = 1L;

		doThrow(new UnprocessableEntityException("Não existe categoria com o id: " + id)).when(categoriaService)
		.deletarCategoria(anyLong());

		MvcResult result = mockMvc.perform( //
				delete("/categorias/" + id) //
				.contentType(APPLICATION_JSON)) //
				.andExpect(status().isUnprocessableEntity()).andReturn();

		JSONAssert.assertEquals(CategoriaMock.CATEGORIA_UNPROCESSABLE_ENTITY,
				result.getResponse().getContentAsString(StandardCharsets.UTF_8), new CustomComparator(
						JSONCompareMode.STRICT, Customization.customization("timestamp", new ValueMatcher<Object>() {
							@Override
							public boolean equal(Object o1, Object o2) {
								return true;
							}
						})));

	}

	@Test
	void return200WheUpdateCategoria() throws Exception {

		Long id = 1L;

		when(categoriaService.alterarCategoria(anyLong(), any())).thenReturn(CategoriaMock.CATEGORIA_RESPONSE);

		MvcResult result = mockMvc.perform( //
				put("/categorias/" + id) //
				.contentType(APPLICATION_JSON).content(toString(CategoriaMock.CATEGORIA))) //
				.andExpect(status().isOk()).andReturn();

		assertEquals(toString(CategoriaMock.CATEGORIA_RESPONSE), result.getResponse().getContentAsString());

	}

	@Test
	void return422WheUpdateCategoriaNotFound() throws Exception {

		Long id = 1L;

		doThrow(new UnprocessableEntityException("Não existe categoria com o id: " + id)).when(categoriaService)
		.alterarCategoria(anyLong(), any());

		MvcResult result = mockMvc.perform( //
				put("/categorias/" + id) //
				.contentType(APPLICATION_JSON).content(toString(CategoriaMock.CATEGORIA))) //
				.andExpect(status().isUnprocessableEntity()).andReturn();

		JSONAssert.assertEquals(CategoriaMock.CATEGORIA_UNPROCESSABLE_ENTITY,
				result.getResponse().getContentAsString(StandardCharsets.UTF_8), new CustomComparator(
						JSONCompareMode.STRICT, Customization.customization("timestamp", new ValueMatcher<Object>() {
							@Override
							public boolean equal(Object o1, Object o2) {
								return true;
							}
						})));

	}

	@Test
	void return400WhenUpdateCategoriaWithoutFields() throws Exception {
		Long id = 1L;

		MvcResult result = mockMvc.perform( //
				put("/categorias/" + id) //
				.contentType(APPLICATION_JSON).content(toString(CategoriaMock.CATEGORIA_BAD_REQUEST))) //
				.andExpect(status().isBadRequest()) //
				.andReturn();

		JSONAssert.assertEquals(CategoriaMock.CATEGORIA_RESPONSE_BAD_REQUEST,
				result.getResponse().getContentAsString(StandardCharsets.UTF_8), JSONCompareMode.NON_EXTENSIBLE);

	}

	@Test
	void return200WhenSearchVideosWithCategoria() throws Exception {

		Long id = 1L;

		when(categoriaService.buscarVideosPorCategoria(anyLong())).thenReturn(CategoriaMock.FIND_ALL_CATEGORIA_VIDEO);

		MvcResult result = mockMvc.perform( //
				get("/categorias/" + id + "/videos") //
				.contentType(APPLICATION_JSON)) //
				.andExpect(status().isOk()) //
				.andReturn();

		JSONAssert.assertEquals(toString(CategoriaMock.FIND_ALL_CATEGORIA_VIDEO),
				result.getResponse().getContentAsString(StandardCharsets.UTF_8), JSONCompareMode.NON_EXTENSIBLE);

	}

	@Test
	void return422WhenSearchVideoCategoriaNotFound() throws Exception {
		Long id = 1L;

		doThrow(new UnprocessableEntityException("Não existe categoria com o id: " + id)).when(categoriaService)
		.buscarVideosPorCategoria(anyLong());

		MvcResult result = mockMvc.perform( //
				get("/categorias/" + id + "/videos") //
				.contentType(APPLICATION_JSON)) //
				.andExpect(status().isUnprocessableEntity()).andReturn();

		JSONAssert.assertEquals(CategoriaMock.CATEGORIA_UNPROCESSABLE_ENTITY_VIDEO,
				result.getResponse().getContentAsString(StandardCharsets.UTF_8), new CustomComparator(
						JSONCompareMode.STRICT, Customization.customization("timestamp", new ValueMatcher<Object>() {
							@Override
							public boolean equal(Object o1, Object o2) {
								return true;
							}
						})));
	}

	@Test
	void return404WhenSearchVideoCategoriaNotFound() throws Exception {
		Long id = 1L;

		doThrow(new NotFoundException("Não há videos cadastrados para essa categoria!")).when(categoriaService)
		.buscarVideosPorCategoria(anyLong());

		MvcResult result = mockMvc.perform( //
				get("/categorias/" + id + "/videos") //
				.contentType(APPLICATION_JSON)) //
				.andExpect(status().isNotFound()).andReturn();

		JSONAssert.assertEquals(CategoriaMock.CATEGORIA_FIND_ALL_NOT_FOUND_VIDEO,
				result.getResponse().getContentAsString(StandardCharsets.UTF_8), new CustomComparator(
						JSONCompareMode.STRICT, Customization.customization("timestamp", new ValueMatcher<Object>() {
							@Override
							public boolean equal(Object o1, Object o2) {
								return true;
							}
						})));
	}

	private String toString(final Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}
}
