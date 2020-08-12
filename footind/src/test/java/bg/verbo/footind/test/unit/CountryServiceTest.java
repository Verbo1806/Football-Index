package bg.verbo.footind.test.unit;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import bg.verbo.footind.db.entity.Country;
import bg.verbo.footind.db.repository.CountryRepository;
import bg.verbo.footind.dto.CountryDTO;
import bg.verbo.footind.service.CountryService;

@ExtendWith(MockitoExtension.class)
public class CountryServiceTest {

	private CountryService service;
	private Country country;
	private ArrayList<Country> allCountries;

	@Mock
	CountryRepository mockCountryRepository;

	@Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        service = new CountryService(mockCountryRepository);
        
        country = new Country();
        country.setCode("New code");
        country.setName("New name");
	    
        allCountries = new ArrayList<>();
        allCountries.add(country);
    }
	
	@Test
	public void testFindAll() {
	    when(mockCountryRepository.findAll()).
	        thenReturn(allCountries);

	    List<CountryDTO> countryDTOs = service.findAll();

	    Assertions.assertEquals(1, countryDTOs.size());
	    CountryDTO actualDTO = countryDTOs.get(0);

	    Assertions.assertEquals(country.getName(), actualDTO.getName());
	    Assertions.assertEquals(country.getCode(), actualDTO.getCode());
	}
	
}
