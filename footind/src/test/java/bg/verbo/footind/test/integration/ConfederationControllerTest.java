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

import bg.verbo.footind.db.entity.Confederation;
import bg.verbo.footind.db.repository.ConfederationRepository;
import bg.verbo.footind.dto.ConfederationDTO;

@SpringBootTest
@AutoConfigureMockMvc
class ConfederationControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ConfederationRepository confederationRepository;
	
	private Confederation confederation1, confederation2;
	
	@BeforeEach
	public void setUp() {
		confederationRepository.deleteAll();

	    confederation1 = new Confederation();
	    confederation1.setName("conf1");
	    confederation1 = confederationRepository.save(confederation1);

	    confederation2 = new Confederation();
	    confederation2.setName("conf2");
	    confederation2 = confederationRepository.save(confederation2);
	}

	@AfterEach
	public void tearDown() {
		confederationRepository.deleteAll();
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testsReturnsCorrectStatusCode() throws Exception {
		this.mockMvc.perform(get("/confederations")).
			andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testNotFound() throws Exception {
		this.mockMvc.perform(get("/confederations/{confederationId}", 7777)).
	        andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testFound() throws Exception {
	    this.mockMvc.
	        perform(get("/confederations/{confederationId}", confederation1.getId())).
	        andExpect(status().isOk()).
	        andExpect(jsonPath("$.name").value(confederation1.getName()));
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testAll() throws Exception {
	    this.mockMvc.
	        perform(get("/confederations")).
	        andExpect(status().isOk()).
	        andExpect(jsonPath("$.[0].id").value(confederation1.getId())).
	        andExpect(jsonPath("$.[0].name").value(confederation1.getName())).
	        andExpect(jsonPath("$.[1].id").value(confederation2.getId())).
	        andExpect(jsonPath("$.[1].name").value(confederation2.getName()));
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testCreate() throws Exception {
		ConfederationDTO newConf = new ConfederationDTO();
		newConf.setName("New Conf");
		newConf.setFounded("2020");

	    this.mockMvc.
	        perform(post("/confederations")
	        .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(newConf)))
	        .andExpect(status().is(201));
	}
	
	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testPut() throws Exception {
		confederation1.setName("conf");
		
	    this.mockMvc.
	        perform(put("/confederations/{confederationId}", confederation1.getId())
	        .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(confederation1)))
	        .andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testDelete() throws Exception {
	    this.mockMvc.
	        perform(delete("/confederations/{confederationId}", confederation2.getId())
	        .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(confederation1)))
	        .andExpect(status().isNoContent());
	}
}
