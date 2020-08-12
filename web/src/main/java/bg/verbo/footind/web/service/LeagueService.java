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
import bg.verbo.footind.web.dto.LeagueDTO;
import bg.verbo.footind.web.http.GenericRequests;
import bg.verbo.footind.web.util.QueryParamUtil;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LeagueService {
	
	private GenericRequests<LeagueDTO> requestBuilder;
	private ObjectMapper mapper;
	
	public List<LeagueDTO> findAll() {
		ResponseEntity<List<LeagueDTO>> results 
			= requestBuilder.findAll(Config.BASE_PATH + Config.LEAGUES_URL);
	
	    if (results.hasBody()) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<List<LeagueDTO>>(){}
	    		);
	    }
	    
	    return Collections.emptyList();
	}
	
	public LeagueDTO findById(Long leagueId) {
		ResponseEntity<LeagueDTO> results 
			= requestBuilder.findById(Config.BASE_PATH + Config.LEAGUES_URL + "/" + leagueId);
		
	    if (results.getStatusCodeValue() == 200) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<LeagueDTO>(){}
	    		);
	    } else {
	    	return null;
	    }
	}
	
	public List<LeagueDTO> findByExample(LeagueDTO league) {
		Map<String, String> params 
			= QueryParamUtil.build();
		
		ResponseEntity<List<LeagueDTO>> results 
			= requestBuilder.findByExample(Config.BASE_PATH + Config.LEAGUES_URL, params);
	
	    if (results.getStatusCodeValue() == 200) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<List<LeagueDTO>>(){}
	    		);
	    } else {
	    	return Collections.emptyList();
	    }
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	public LeagueDTO create(LeagueDTO league) {
		ResponseEntity<LeagueDTO> results 
			= requestBuilder.create(Config.BASE_PATH + Config.LEAGUES_URL, league);
	
	    if (results.getStatusCodeValue() == 201) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<LeagueDTO>(){}
	    		);
	    } else {
	    	return null;
	    }
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	public LeagueDTO update(LeagueDTO league, Long leagueId) {
		ResponseEntity<LeagueDTO> results 
			= requestBuilder.update(Config.BASE_PATH + Config.LEAGUES_URL + "/" + leagueId, league);
	
	    if (results.getStatusCodeValue() == 200) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<LeagueDTO>(){}
	    		);
	    } else {
	    	return null;
	    }
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	public boolean delete(Long leagueId) {
		ResponseEntity<LeagueDTO> results 
			= requestBuilder.delete(Config.BASE_PATH + Config.LEAGUES_URL + "/" + leagueId);
	
	    return results.getStatusCodeValue() == 204;
	}
	
}
