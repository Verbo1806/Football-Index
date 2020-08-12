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

import bg.verbo.footind.db.entity.Player;
import bg.verbo.footind.db.repository.PlayerRepository;
import bg.verbo.footind.dto.PlayerDTO;

@SpringBootTest
@AutoConfigureMockMvc
class PlayerControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	private Player player1, player2;
	
	@BeforeEach
	public void setUp() {
		playerRepository.deleteAll();

	    player1 = new Player();
	    player1.setFullName("player1");
	    player1 = playerRepository.save(player1);

	    player2 = new Player();
	    player2.setFullName("player2");
	    player2 = playerRepository.save(player2);
	}

	@AfterEach
	public void tearDown() {
		playerRepository.deleteAll();
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testsReturnsCorrectStatusCode() throws Exception {
		this.mockMvc.perform(get("/players")).
			andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testNotFound() throws Exception {
		this.mockMvc.perform(get("/players/{playerId}", 7777)).
	        andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testFound() throws Exception {
	    this.mockMvc.
	        perform(get("/players/{playerId}", player1.getId())).
	        andExpect(status().isOk()).
	        andExpect(jsonPath("$.fullName").value(player1.getFullName()));
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testAll() throws Exception {
	    this.mockMvc.
	        perform(get("/players")).
	        andExpect(status().isOk()).
	        andExpect(jsonPath("$.[0].id").value(player1.getId())).
	        andExpect(jsonPath("$.[0].fullName").value(player1.getFullName())).
	        andExpect(jsonPath("$.[1].id").value(player2.getId())).
	        andExpect(jsonPath("$.[1].fullName").value(player2.getFullName()));
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testCreate() throws Exception {
		PlayerDTO newPlayer = new PlayerDTO();
		newPlayer.setFullName("New Player");
		newPlayer.setShirtNumber((short) 19);

	    this.mockMvc.
	        perform(post("/players")
	        .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(newPlayer)))
	        .andExpect(status().is(201));
	}
	
	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testPut() throws Exception {
		player1.setFullName("pplayer");
		
	    this.mockMvc.
	        perform(put("/players/{playerId}", player1.getId())
	        .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(player1)))
	        .andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser(username = "Da", authorities={"USER", "ADMIN"})
	void testDelete() throws Exception {
	    this.mockMvc.
	        perform(delete("/players/{playerId}", player2.getId())
	        .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(player1)))
	        .andExpect(status().isNoContent());
	}
}
