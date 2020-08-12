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
import bg.verbo.footind.web.dto.PlayerDTO;
import bg.verbo.footind.web.http.GenericRequests;
import bg.verbo.footind.web.util.QueryParamUtil;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PlayerService {
	
	private GenericRequests<PlayerDTO> requestBuilder;
	private ObjectMapper mapper;
	
	public List<PlayerDTO> findAll() {
		ResponseEntity<List<PlayerDTO>> results 
			= requestBuilder.findAll(Config.BASE_PATH + Config.PLAYERS_URL);
	
	    if (results.hasBody()) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<List<PlayerDTO>>(){}
	    		);
	    }
	    
	    return Collections.emptyList();
	}
	
	public PlayerDTO findById(Long playerId) {
		ResponseEntity<PlayerDTO> results 
			= requestBuilder.findById(Config.BASE_PATH + Config.PLAYERS_URL + "/" + playerId);
		
	    if (results.getStatusCodeValue() == 200) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<PlayerDTO>(){}
	    		);
	    } else {
	    	return null;
	    }
	}
	
	public List<PlayerDTO> findByExample(PlayerDTO player) {
		Map<String, String> params 
			= QueryParamUtil.build();
		
		ResponseEntity<List<PlayerDTO>> results 
			= requestBuilder.findByExample(Config.BASE_PATH + Config.PLAYERS_URL, params);
	
	    if (results.getStatusCodeValue() == 200) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<List<PlayerDTO>>(){}
	    		);
	    } else {
	    	return Collections.emptyList();
	    }
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	public PlayerDTO create(PlayerDTO player) {
		ResponseEntity<PlayerDTO> results 
			= requestBuilder.create(Config.BASE_PATH + Config.PLAYERS_URL, player);
	
	    if (results.getStatusCodeValue() == 201) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<PlayerDTO>(){}
	    		);
	    } else {
	    	return null;
	    }
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	public PlayerDTO update(PlayerDTO player, Long playerId) {
		ResponseEntity<PlayerDTO> results 
			= requestBuilder.update(Config.BASE_PATH + Config.PLAYERS_URL + "/" + playerId, player);
	
	    if (results.getStatusCodeValue() == 200) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<PlayerDTO>(){}
	    		);
	    } else {
	    	return null;
	    }
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	public boolean delete(Long playerId) {
		ResponseEntity<PlayerDTO> results 
			= requestBuilder.delete(Config.BASE_PATH + Config.PLAYERS_URL + "/" + playerId);
	
	    return results.getStatusCodeValue() == 204;
	}
	
}
