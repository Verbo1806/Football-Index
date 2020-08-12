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

import bg.verbo.footind.db.entity.Team;
import bg.verbo.footind.db.repository.PlayerRepository;
import bg.verbo.footind.db.repository.TeamRepository;
import bg.verbo.footind.dto.TeamDTO;

@SpringBootTest
@AutoConfigureMockMvc
class TeamControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	private String TEST_TEAM1_NAME = "Levski Sofia", TEST_TEAM2_NAME = "Spartak Varna";
	private Team team1, team2;
	
	@BeforeEach
	public void setUp() {
		playerRepository.deleteAll();
		teamRepository.deleteAll();

	    team1 = new Team();
	    team1.setName((TEST_TEAM1_NAME));
	    team1 = teamRepository.save(team1);

	    team2 = new Team();
	    team2.setName((TEST_TEAM2_NAME));
	    team2 = teamRepository.save(team2);
	}

	@AfterEach
	public void tearDown() {
		teamRepository.deleteAll();
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testsReturnsCorrectStatusCode() throws Exception {
		this.mockMvc.perform(get("/teams")).
			andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testNotFound() throws Exception {
		this.mockMvc.perform(get("/teams/{teamId}", 7777)).
	        andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testFound() throws Exception {
	    this.mockMvc.
	        perform(get("/teams/{teamId}", team1.getId())).
	        andExpect(status().isOk()).
	        andExpect(jsonPath("$.name").value(TEST_TEAM1_NAME));
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testAll() throws Exception {
	    this.mockMvc.
	        perform(get("/teams")).
	        andExpect(status().isOk()).
	        andExpect(jsonPath("$.[0].id").value(team1.getId())).
	        andExpect(jsonPath("$.[0].name").value(TEST_TEAM1_NAME)).
	        andExpect(jsonPath("$.[1].id").value(team2.getId())).
	        andExpect(jsonPath("$.[1].name").value(TEST_TEAM2_NAME));
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testCreate() throws Exception {
		TeamDTO newTeam = new TeamDTO();
		newTeam.setAbbreaviature("NEW");
		newTeam.setName("New Team");
		newTeam.setFounded("2020");

	    this.mockMvc.
	        perform(post("/teams")
	        .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(newTeam)))
	        .andExpect(status().is(201));
	}
	
	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testPut() throws Exception {
		team1.setFounded("2020");
		
	    this.mockMvc.
	        perform(put("/teams/{teamId}", team1.getId())
	        .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(team1)))
	        .andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testDelete() throws Exception {
	    this.mockMvc.
	        perform(delete("/teams/{teamId}", team2.getId())
	        .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(team1)))
	        .andExpect(status().isNoContent());
	}
}
