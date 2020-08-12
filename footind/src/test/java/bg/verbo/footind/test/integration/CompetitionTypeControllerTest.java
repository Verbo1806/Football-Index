package bg.verbo.footind.test.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import bg.verbo.footind.db.entity.CompetitionType;
import bg.verbo.footind.db.repository.CompetitionTypeRepository;
import bg.verbo.footind.db.repository.LeagueRepository;

@SpringBootTest
@AutoConfigureMockMvc
class CompetitionTypeControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CompetitionTypeRepository competitionTypeRepository;
	
	@Autowired
	private LeagueRepository leagueRepository;
	
	private CompetitionType type1, type2;
	
	@BeforeEach
	public void setUp() {
		leagueRepository.deleteAll();
		competitionTypeRepository.deleteAll();

		type1 = new CompetitionType();
	    type1.setCode("code1");
	    type1.setName("name1");
	    type1 = competitionTypeRepository.save(type1);

	    type2 = new CompetitionType();
	    type2.setCode("code2");
	    type2.setName("name2");
	    type2 = competitionTypeRepository.save(type2);
	}

	@AfterEach
	public void tearDown() {
		competitionTypeRepository.deleteAll();
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER"})
	void testsReturnsCorrectStatusCode() throws Exception {
		this.mockMvc.perform(get("/competitions")).
			andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER"})
	void testAll() throws Exception {
	    this.mockMvc.
	        perform(get("/competitions")).
	        andExpect(status().isOk()).
	        andExpect(jsonPath("$.[0].code").value(type1.getCode())).
	        andExpect(jsonPath("$.[0].name").value(type1.getName())).
	        andExpect(jsonPath("$.[1].code").value(type2.getCode())).
	        andExpect(jsonPath("$.[1].name").value(type2.getName()));
	}
}
