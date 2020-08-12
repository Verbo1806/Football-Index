package bg.verbo.footind.web.service;

import java.util.Collections;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import bg.verbo.footind.web.config.Config;
import bg.verbo.footind.web.dto.SurfaceTypeDTO;
import bg.verbo.footind.web.http.GenericRequests;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SurfaceService {
	
	private GenericRequests<SurfaceTypeDTO> requestBuilder;
	private ObjectMapper mapper;
	
	public List<SurfaceTypeDTO> findAll() {
		ResponseEntity<List<SurfaceTypeDTO>> results 
			= requestBuilder.findAll(Config.BASE_PATH + Config.SURFACE_TYPES_URL);
	
	    if (results.hasBody()) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<List<SurfaceTypeDTO>>(){}
	    		);
	    }
	    
	    return Collections.emptyList();
	}
	
}
