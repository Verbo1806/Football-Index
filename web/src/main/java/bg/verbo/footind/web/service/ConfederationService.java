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
import bg.verbo.footind.web.dto.ConfederationDTO;
import bg.verbo.footind.web.http.GenericRequests;
import bg.verbo.footind.web.util.QueryParamUtil;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ConfederationService {
	
	private GenericRequests<ConfederationDTO> requestBuilder;
	private ObjectMapper mapper;
	
	public List<ConfederationDTO> findAll() {
		ResponseEntity<List<ConfederationDTO>> results 
			= requestBuilder.findAll(Config.BASE_PATH + Config.CONFEDERATIONS_URL);

	    if (results.hasBody()) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<List<ConfederationDTO>>(){}
	    		);
	    }
	    
	    return Collections.emptyList();
	}
	
	public ConfederationDTO findById(Long confederationId) {
		ResponseEntity<ConfederationDTO> results 
			= requestBuilder.findById(Config.BASE_PATH + Config.CONFEDERATIONS_URL + "/" + confederationId);
		
	    if (results.getStatusCodeValue() == 200) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<ConfederationDTO>(){}
	    		);
	    } else {
	    	return null;
	    }
	}
	
	public List<ConfederationDTO> findByExample(ConfederationDTO confederation) {
		Map<String, String> params 
			= QueryParamUtil.build();
		
		ResponseEntity<List<ConfederationDTO>> results 
			= requestBuilder.findByExample(Config.BASE_PATH + Config.CONFEDERATIONS_URL, params);

	    if (results.getStatusCodeValue() == 200) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<List<ConfederationDTO>>(){}
	    		);
	    } else {
	    	return Collections.emptyList();
	    }
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	public ConfederationDTO create(ConfederationDTO confederation) {
		ResponseEntity<ConfederationDTO> results 
			= requestBuilder.create(Config.BASE_PATH + Config.CONFEDERATIONS_URL, confederation);

	    if (results.getStatusCodeValue() == 201) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<ConfederationDTO>(){}
	    		);
	    } else {
	    	return null;
	    }
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	public ConfederationDTO update(ConfederationDTO confederation, Long confederationId) {
		ResponseEntity<ConfederationDTO> results 
			= requestBuilder.update(Config.BASE_PATH + Config.CONFEDERATIONS_URL + "/" + confederationId, confederation);

	    if (results.getStatusCodeValue() == 200) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<ConfederationDTO>(){}
	    		);
	    } else {
	    	return null;
	    }
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	public boolean delete(Long confederationId) {
		ResponseEntity<ConfederationDTO> results 
			= requestBuilder.delete(Config.BASE_PATH + Config.CONFEDERATIONS_URL + "/" + confederationId);

	    return results.getStatusCodeValue() == 204;
	}
}
