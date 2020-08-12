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

import bg.verbo.footind.db.entity.StadiumCategory;
import bg.verbo.footind.db.repository.GroundRepository;
import bg.verbo.footind.db.repository.StadiumCategoryRepository;

@SpringBootTest
@AutoConfigureMockMvc
class StadiumCategoryControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private StadiumCategoryRepository stadiumCategoryRepository;
	
	@Autowired
	private GroundRepository groundRepository;
	
	private StadiumCategory category1, category2;
	
	@BeforeEach
	public void setUp() {
		groundRepository.deleteAll();
		stadiumCategoryRepository.deleteAll();

		category1 = new StadiumCategory();
	    category1.setCode("code1");
	    category1.setName("name1");
	    category1 = stadiumCategoryRepository.save(category1);

	    category2 = new StadiumCategory();
	    category2.setCode("code2");
	    category2.setName("name2");
	    category2 = stadiumCategoryRepository.save(category2);
	}

	@AfterEach
	public void tearDown() {
		stadiumCategoryRepository.deleteAll();
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER"})
	void testsReturnsCorrectStatusCode() throws Exception {
		this.mockMvc.perform(get("/stadium_categories")).
			andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER"})
	void testAll() throws Exception {
	    this.mockMvc.
	        perform(get("/stadium_categories")).
	        andExpect(status().isOk()).
	        andExpect(jsonPath("$.[0].code").value(category1.getCode())).
	        andExpect(jsonPath("$.[0].name").value(category1.getName())).
	        andExpect(jsonPath("$.[1].code").value(category2.getCode())).
	        andExpect(jsonPath("$.[1].name").value(category2.getName()));
	}
}
