package bg.verbo.footind.web.service;

import java.util.Collections;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import bg.verbo.footind.web.config.Config;
import bg.verbo.footind.web.dto.CountryDTO;
import bg.verbo.footind.web.http.GenericRequests;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CountryService {
	
	private GenericRequests<CountryDTO> requestBuilder;
	private ObjectMapper mapper;
	
	public List<CountryDTO> findAll() {
		ResponseEntity<List<CountryDTO>> results 
			= requestBuilder.findAll(Config.BASE_PATH + Config.COUNTRIES_URL);
			
		    if (results.hasBody()) {
		    	return mapper.convertValue(
		    			results.getBody(), 
		    		    new TypeReference<List<CountryDTO>>(){}
		    		);
		    }
		    
		    return Collections.emptyList();
	}
	
}
