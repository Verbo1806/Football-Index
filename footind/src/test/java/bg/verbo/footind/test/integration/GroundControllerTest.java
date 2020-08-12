package bg.verbo.footind.test.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import bg.verbo.footind.db.entity.Ground;
import bg.verbo.footind.db.repository.GroundRepository;
import bg.verbo.footind.dto.GroundDTO;

@SpringBootTest
@AutoConfigureMockMvc
class GroundControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private GroundRepository groundRepository;
	
	private Ground ground1, ground2;
	
	@BeforeEach
	public void setUp() {
		groundRepository.deleteAll();

	    ground1 = new Ground();
	    ground1.setName("ground1");
	    ground1 = groundRepository.save(ground1);

	    ground2 = new Ground();
	    ground2.setName("ground2");
	    ground2 = groundRepository.save(ground2);
	}

	@AfterEach
	public void tearDown() {
		groundRepository.deleteAll();
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testsReturnsCorrectStatusCode() throws Exception {
		this.mockMvc.perform(get("/grounds")).
			andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testNotFound() throws Exception {
		this.mockMvc.perform(get("/grounds/{groundId}", 7777)).
	        andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testFound() throws Exception {
	    this.mockMvc.
	        perform(get("/grounds/{groundId}", ground1.getId())).
	        andExpect(status().isOk()).
	        andExpect(jsonPath("$.name").value(ground1.getName()));
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testAll() throws Exception {
	    this.mockMvc.
	        perform(get("/grounds")).
	        andExpect(status().isOk()).
	        andExpect(jsonPath("$.[0].id").value(ground1.getId())).
	        andExpect(jsonPath("$.[0].name").value(ground1.getName())).
	        andExpect(jsonPath("$.[1].id").value(ground2.getId())).
	        andExpect(jsonPath("$.[1].name").value(ground2.getName()));
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testCreate() throws Exception {
		GroundDTO newGround = new GroundDTO();
		newGround.setName("New Ground");
		newGround.setFounded("2020");

	    this.mockMvc.
	        perform(post("/grounds")
	        .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(newGround)))
	        .andExpect(status().is(201));
	}
	
	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testPut() throws Exception {
		ground1.setFounded("2021");
		
	    this.mockMvc.
	        perform(put("/grounds/{groundId}", ground1.getId())
	        .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(ground1)))
	        .andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testDelete() throws Exception {
	    this.mockMvc.
	        perform(delete("/grounds/{groundId}", ground2.getId())
	        .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(ground1)))
	        .andExpect(status().isNoContent());
	}
}
