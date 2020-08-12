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

import bg.verbo.footind.db.entity.Country;
import bg.verbo.footind.db.repository.CountryRepository;

@SpringBootTest
@AutoConfigureMockMvc
class CountryControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CountryRepository countryRepository;
	
	private Country country1, country2;
	
	@BeforeEach
	public void setUp() {
		countryRepository.deleteAll();

		country1 = new Country();
	    country1.setCode("code1");
	    country1.setName("name1");
	    country1 = countryRepository.save(country1);

	    country2 = new Country();
	    country2.setCode("code2");
	    country2.setName("name2");
	    country2 = countryRepository.save(country2);
	}

	@AfterEach
	public void tearDown() {
		countryRepository.deleteAll();
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER"})
	void testsReturnsCorrectStatusCode() throws Exception {
		this.mockMvc.perform(get("/countries")).
			andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "Da", authorities={"USER"})
	void testAll() throws Exception {
	    this.mockMvc.
	        perform(get("/countries")).
	        andExpect(status().isOk()).
	        andExpect(jsonPath("$.[0].code").value(country1.getCode())).
	        andExpect(jsonPath("$.[0].name").value(country1.getName())).
	        andExpect(jsonPath("$.[1].code").value(country2.getCode())).
	        andExpect(jsonPath("$.[1].name").value(country2.getName()));
	}
}
