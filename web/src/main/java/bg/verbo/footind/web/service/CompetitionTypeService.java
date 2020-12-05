package bg.verbo.footind.web.service;

import java.util.Collections;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import bg.verbo.footind.web.config.Config;
import bg.verbo.footind.web.dto.CompetitionTypeDTO;
import bg.verbo.footind.web.http.GenericRequests;

@Service
public class CompetitionTypeService extends GenericRequests<CompetitionTypeDTO> {
	
	public CompetitionTypeService(RestTemplate template) {
		super(template, CompetitionTypeDTO.class);
	}
	
	public List<CompetitionTypeDTO> findAll() {
		ResponseEntity<List<CompetitionTypeDTO>> results 
			= findAll(Config.BASE_PATH + Config.COMPETITION_TYPES_URL);
				
	    if (results.hasBody()) {
	    	return results.getBody();
	    }
	    
	    return Collections.emptyList();
	}
	
}
