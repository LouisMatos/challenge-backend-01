package br.com.luis.challengebackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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
	void returStatus200WhenFindAllVideos() throws Exception {

		when(videoService.listarTodosVideos(any())).thenReturn(VideoMock.FIND_ALL);

		mockMvc.perform( //
				get("/videos") //
				.contentType(APPLICATION_JSON)) //
		.andExpect(status().isOk());

	}

	@Test
	void returnStatus400WhenSaveNewVideoWithoutFields() throws Exception {

		MvcResult result = mockMvc.perform( //
				post("/videos") //
				.contentType(APPLICATION_JSON) //
				.content(toString(VideoMock.VIDEO_BAD_REQUEST))) //
				.andExpect(status().isBadRequest()) //
				.andReturn();

		assertEquals(parserString(VideoMock.VIDEO_RESPONSE_BAD_REQUEST),
				parserString(result.getResponse().getContentAsString(StandardCharsets.UTF_8)));
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
	void return202WhenDeleteVideo() throws Exception {


		mockMvc.perform( //
				delete("/videos/1") //
				.contentType(APPLICATION_JSON) //
				.content(toString(VideoMock.VIDEO_WITHOUT_CATEGORY))) //
		.andExpect(status().isAccepted());

	}

	private String toString(final Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	@SuppressWarnings("deprecation")
	private JsonElement parserString(String string) {
		JsonParser parser = new JsonParser();

		JsonElement json1 = parser.parse(string);

		return json1;
	}

}
