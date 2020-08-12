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

import bg.verbo.footind.db.entity.League;
import bg.verbo.footind.db.repository.LeagueRepository;
import bg.verbo.footind.dto.LeagueDTO;

@SpringBootTest
@AutoConfigureMockMvc
class LeagueControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private LeagueRepository leagueRepository;
	
	private League league1, league2;
	
	@BeforeEach
	public void setUp() {
		leagueRepository.deleteAll();

	    league1 = new League();
	    league1.setName("league1");
	    league1 = leagueRepository.save(league1);

	    league2 = new League();
	    league2.setName("league2");
	    league2 = leagueRepository.save(league2);
	}

	@AfterEach
	public void tearDown() {
		leagueRepository.deleteAll();
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testsReturnsCorrectStatusCode() throws Exception {
		this.mockMvc.perform(get("/leagues")).
			andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testNotFound() throws Exception {
		this.mockMvc.perform(get("/leagues/{leagueId}", 7777)).
	        andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testFound() throws Exception {
	    this.mockMvc.
	        perform(get("/leagues/{leagueId}", league1.getId())).
	        andExpect(status().isOk()).
	        andExpect(jsonPath("$.name").value(league1.getName()));
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testAll() throws Exception {
	    this.mockMvc.
	        perform(get("/leagues")).
	        andExpect(status().isOk()).
	        andExpect(jsonPath("$.[0].id").value(league1.getId())).
	        andExpect(jsonPath("$.[0].name").value(league1.getName())).
	        andExpect(jsonPath("$.[1].id").value(league2.getId())).
	        andExpect(jsonPath("$.[1].name").value(league2.getName()));
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testCreate() throws Exception {
		LeagueDTO newLeague = new LeagueDTO();
		newLeague.setName("New League");
		newLeague.setFounded("2020");

	    this.mockMvc.
	        perform(post("/leagues")
	        .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(newLeague)))
	        .andExpect(status().is(201));
	}
	
	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testPut() throws Exception {
		league1.setFounded("2021");
		
	    this.mockMvc.
	        perform(put("/leagues/{leagueId}", league1.getId())
	        .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(league1)))
	        .andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testDelete() throws Exception {
	    this.mockMvc.
	        perform(delete("/leagues/{leagueId}", league2.getId())
	        .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(league1)))
	        .andExpect(status().isNoContent());
	}
}
