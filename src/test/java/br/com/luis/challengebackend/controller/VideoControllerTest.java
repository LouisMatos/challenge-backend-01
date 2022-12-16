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
import br.com.luis.challengebackend.mock.VideoMock;
import br.com.luis.challengebackend.service.VideoService;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VideoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private VideoService videoService;

	@Test
	void returnStatus200WhenSaveNewVideo() throws Exception {

		when(videoService.salvarVideo(any())).thenReturn(VideoMock.VIDEO_RESPONSE);

		MvcResult result = mockMvc.perform( //
				post("/videos") //
				.contentType(APPLICATION_JSON) //
				.content(toString(VideoMock.VIDEO))) //
				.andExpect(status().isOk()) //
				.andReturn();

		assertEquals(toString(VideoMock.VIDEO_RESPONSE), result.getResponse().getContentAsString());

	}

	@Test
	void returnStatus200WhenSaveNewVideoWithoutCategory() throws Exception {

		when(videoService.salvarVideo(any())).thenReturn(VideoMock.VIDEO_RESPONSE);

		MvcResult result = mockMvc.perform( //
				post("/videos") //
				.contentType(APPLICATION_JSON) //
				.content(toString(VideoMock.VIDEO_WITHOUT_CATEGORY))) //
				.andExpect(status().isOk()) //
				.andReturn();

		assertEquals(toString(VideoMock.VIDEO_RESPONSE), result.getResponse().getContentAsString());

	}

	@Test
	void returnStatus400WhenSaveNewVideoWithoutFields() throws Exception {

		MvcResult result = mockMvc.perform( //
				post("/videos") //
				.contentType(APPLICATION_JSON) //
				.content(toString(VideoMock.VIDEO_BAD_REQUEST))) //
				.andExpect(status().isBadRequest()) //
				.andReturn();

		JSONAssert.assertEquals(VideoMock.VIDEO_RESPONSE_BAD_REQUEST,
				result.getResponse().getContentAsString(StandardCharsets.UTF_8), JSONCompareMode.NON_EXTENSIBLE);

	}

	@Test
	void returnStatus200WhenSearchVideoWithID() throws Exception {

		when(videoService.buscarVideoPorId(any())).thenReturn(VideoMock.VIDEO_RESPONSE);

		MvcResult result = mockMvc.perform( //
				get("/videos/1") //
				.contentType(APPLICATION_JSON)) //
				.andExpect(status().isOk()) //
				.andReturn();

		assertEquals(toString(VideoMock.VIDEO_RESPONSE), result.getResponse().getContentAsString());

	}

	@Test
	void returnStatus404WhenSearchVideoNotFound() throws Exception {

		Long id = 1L;

		when(videoService.buscarVideoPorId(any()))
		.thenThrow(new NotFoundException("Nenhum video foi encontrado com o id informado: " + id));

		MvcResult result = mockMvc.perform( //
				get("/videos/" + id) //
				.contentType(APPLICATION_JSON)) //
				.andExpect(status().isNotFound()) //
				.andReturn();

		JSONAssert.assertEquals(VideoMock.VIDEO_NOT_FOUND,
				result.getResponse().getContentAsString(StandardCharsets.UTF_8), new CustomComparator(
						JSONCompareMode.STRICT, Customization.customization("timestamp", new ValueMatcher<Object>() {
							@Override
							public boolean equal(Object o1, Object o2) {
								return true;
							}
						})));
	}

	@Test
	void returStatus200WhenFindFreeVideos() throws Exception {

		when(videoService.free()).thenReturn(VideoMock.FIND_ALL);

		MvcResult result = mockMvc.perform( //
				get("/videos/free") //
				.contentType(APPLICATION_JSON)) //
				.andExpect(status().isOk()).andReturn();

		assertEquals(toString(VideoMock.FIND_ALL), result.getResponse().getContentAsString());

	}

	@Test
	void returnStatus404WhenFindFreeVideosNotFound() throws Exception {

		when(videoService.free())
		.thenThrow(new NotFoundException("Não há videos cadastrados!"));

		MvcResult result = mockMvc.perform( //
				get("/videos/free") //
				.contentType(APPLICATION_JSON)) //
				.andExpect(status().isNotFound()).andReturn();

		JSONAssert.assertEquals(VideoMock.VIDEO_FIND_FREE_NOT_FOUND,
				result.getResponse().getContentAsString(StandardCharsets.UTF_8), new CustomComparator(
						JSONCompareMode.STRICT, Customization.customization("timestamp", new ValueMatcher<Object>() {
							@Override
							public boolean equal(Object o1, Object o2) {
								return true;
							}
						})));
	}

	@Test
	void returStatus200WhenFindAllVideos() throws Exception {

		when(videoService.listarTodosVideos(any(), any())).thenReturn(VideoMock.FIND_ALL);

		MvcResult result = mockMvc.perform( //
				get("/videos?page=0") //
				.contentType(APPLICATION_JSON)) //
				.andExpect(status().isOk()).andReturn();

		assertEquals(toString(VideoMock.FIND_ALL), result.getResponse().getContentAsString());

	}

	@Test
	void returnStatus404WhenFindAllVideosNotFound() throws Exception {

		when(videoService.listarTodosVideos(any(), any()))
		.thenThrow(new NotFoundException("Não há videos cadastrados!"));

		MvcResult result = mockMvc.perform( //
				get("/videos?page=0") //
				.contentType(APPLICATION_JSON)) //
				.andExpect(status().isNotFound()).andReturn();

		JSONAssert.assertEquals(VideoMock.VIDEO_FIND_ALL_NOT_FOUND,
				result.getResponse().getContentAsString(StandardCharsets.UTF_8), new CustomComparator(
						JSONCompareMode.STRICT, Customization.customization("timestamp", new ValueMatcher<Object>() {
							@Override
							public boolean equal(Object o1, Object o2) {
								return true;
							}
						})));
	}

	@Test
	void return202WhenDeleteVideo() throws Exception {

		mockMvc.perform( //
				delete("/videos/1") //
				.contentType(APPLICATION_JSON)) //
		.andExpect(status().isAccepted());

	}

	@Test
	void return422WhenDeleteVideoNotFound() throws Exception {

		Long id = 1L;

		doThrow(new UnprocessableEntityException("Não existe video com o id: " + id)).when(videoService)
		.deletarVideo(anyLong());

		MvcResult result = mockMvc.perform( //
				delete("/videos/" + id) //
				.contentType(APPLICATION_JSON)) //
				.andExpect(status().isUnprocessableEntity()).andReturn();

		JSONAssert.assertEquals(VideoMock.VIDEO_UNPROCESSABLE_ENTITY_DELETE,
				result.getResponse().getContentAsString(StandardCharsets.UTF_8), new CustomComparator(
						JSONCompareMode.STRICT, Customization.customization("timestamp", new ValueMatcher<Object>() {
							@Override
							public boolean equal(Object o1, Object o2) {
								return true;
							}
						})));

	}

	@Test
	void return200WhenUpdateVideo() throws Exception {

		when(videoService.alterarVideo(anyLong(), any())).thenReturn(VideoMock.VIDEO_RESPONSE);

		MvcResult result = mockMvc.perform( //
				put("/videos/1") //
				.contentType(APPLICATION_JSON) //
				.content(toString(VideoMock.VIDEO))) //
				.andExpect(status().isOk()) //
				.andReturn();

		assertEquals(toString(VideoMock.VIDEO_RESPONSE), result.getResponse().getContentAsString());
	}

	@Test
	void return422WhenUpdateVideoNotFound() throws Exception {
		Long id = 1L;

		doThrow(new UnprocessableEntityException("Não existe video com o id: " + id)).when(videoService)
		.alterarVideo(anyLong(), any());

		MvcResult result = mockMvc.perform( //
				put("/videos/" + id) //
				.contentType(APPLICATION_JSON) //
				.content(toString(VideoMock.VIDEO))) //
				.andExpect(status().isUnprocessableEntity()) //
				.andReturn();

		JSONAssert.assertEquals(VideoMock.VIDEO_UNPROCESSABLE_ENTITY_DELETE,
				result.getResponse().getContentAsString(StandardCharsets.UTF_8), new CustomComparator(
						JSONCompareMode.STRICT, Customization.customization("timestamp", new ValueMatcher<Object>() {
							@Override
							public boolean equal(Object o1, Object o2) {
								return true;
							}
						})));

	}

	@Test
	void return400WhenUpdateVideoWithoutFields() throws Exception {

		MvcResult result = mockMvc.perform( //
				put("/videos/1") //
				.contentType(APPLICATION_JSON) //
				.content(toString(VideoMock.VIDEO_BAD_REQUEST))) //
				.andExpect(status().isBadRequest()) //
				.andReturn();

		JSONAssert.assertEquals(VideoMock.VIDEO_RESPONSE_BAD_REQUEST,
				result.getResponse().getContentAsString(StandardCharsets.UTF_8), JSONCompareMode.NON_EXTENSIBLE);
	}

	private String toString(final Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}

}
