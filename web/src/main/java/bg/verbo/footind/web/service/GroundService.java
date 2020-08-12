package bg.verbo.footind.web.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import bg.verbo.footind.web.config.Config;
import bg.verbo.footind.web.dto.GroundDTO;
import bg.verbo.footind.web.http.GenericRequests;
import bg.verbo.footind.web.util.QueryParamUtil;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GroundService {
	
	private GenericRequests<GroundDTO> requestBuilder;
	private ObjectMapper mapper;
	
	public List<GroundDTO> findAll() {
		ResponseEntity<List<GroundDTO>> results 
			= requestBuilder.findAll(Config.BASE_PATH + Config.GROUNDS_URL);
	
	    if (results.hasBody()) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<List<GroundDTO>>(){}
	    		);
	    }
	    
	    return Collections.emptyList();
	}
	
	public GroundDTO findById(Long groundId) {
		ResponseEntity<GroundDTO> results 
			= requestBuilder.findById(Config.BASE_PATH + Config.GROUNDS_URL + "/" + groundId);
		
	    if (results.getStatusCodeValue() == 200) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<GroundDTO>(){}
	    		);
	    } else {
	    	return null;
	    }
	}
	
	public List<GroundDTO> findByExample(GroundDTO ground) {
		Map<String, String> params 
			= QueryParamUtil.build();
		
		ResponseEntity<List<GroundDTO>> results 
			= requestBuilder.findByExample(Config.BASE_PATH + Config.GROUNDS_URL, params);
	
	    if (results.getStatusCodeValue() == 200) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<List<GroundDTO>>(){}
	    		);
	    } else {
	    	return Collections.emptyList();
	    }
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	public GroundDTO create(GroundDTO ground) {
		ResponseEntity<GroundDTO> results 
			= requestBuilder.create(Config.BASE_PATH + Config.GROUNDS_URL, ground);
	
	    if (results.getStatusCodeValue() == 201) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<GroundDTO>(){}
	    		);
	    } else {
	    	return null;
	    }
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	public GroundDTO update(GroundDTO ground, Long groundId) {
		ResponseEntity<GroundDTO> results 
			= requestBuilder.update(Config.BASE_PATH + Config.GROUNDS_URL + "/" + groundId, ground);
	
	    if (results.getStatusCodeValue() == 200) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<GroundDTO>(){}
	    		);
	    } else {
	    	return null;
	    }
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	public boolean delete(Long groundId) {
		ResponseEntity<GroundDTO> results 
			= requestBuilder.delete(Config.BASE_PATH + Config.GROUNDS_URL + "/" + groundId);
	
		return results.getStatusCodeValue() == 204;
	}
	
}
