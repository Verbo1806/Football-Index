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
import bg.verbo.footind.web.dto.TeamDTO;
import bg.verbo.footind.web.http.GenericRequests;
import bg.verbo.footind.web.util.QueryParamUtil;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TeamService {
	
	private GenericRequests<TeamDTO> requestBuilder;
	private ObjectMapper mapper;
	
	public List<TeamDTO> findAll() {
		ResponseEntity<List<TeamDTO>> results 
			= requestBuilder.findAll(Config.BASE_PATH + Config.TEAMS_URL);
	
	    if (results.hasBody()) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<List<TeamDTO>>(){}
	    		);
	    }
	    
	    return Collections.emptyList();
	}
	
	public TeamDTO findById(Long teamId) {
		ResponseEntity<TeamDTO> results 
			= requestBuilder.findById(Config.BASE_PATH + Config.TEAMS_URL + "/" + teamId);
		
	    if (results.getStatusCodeValue() == 200) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<TeamDTO>(){}
	    		);
	    } else {
	    	return null;
	    }
	}
	
	public List<TeamDTO> findByExample(TeamDTO team) {
		Map<String, String> params 
			= QueryParamUtil.build();
		
		ResponseEntity<List<TeamDTO>> results 
			= requestBuilder.findByExample(Config.BASE_PATH + Config.TEAMS_URL + "/search", params);
	
	    if (results.getStatusCodeValue() == 200) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<List<TeamDTO>>(){}
	    		);
	    } else {
	    	return Collections.emptyList();
	    }
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	public TeamDTO create(TeamDTO team) {
		ResponseEntity<TeamDTO> results 
			= requestBuilder.create(Config.BASE_PATH + Config.TEAMS_URL, team);
	
	    if (results.getStatusCodeValue() == 201) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<TeamDTO>(){}
	    		);
	    } else {
	    	return null;
	    }
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	public TeamDTO update(TeamDTO team, Long teamId) {
		ResponseEntity<TeamDTO> results 
			= requestBuilder.update(Config.BASE_PATH + Config.TEAMS_URL + "/" + teamId, team);
	
	    if (results.getStatusCodeValue() == 200) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<TeamDTO>(){}
	    		);
	    } else {
	    	return null;
	    }
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	public boolean delete(Long teamId) {
		ResponseEntity<TeamDTO> results 
			= requestBuilder.delete(Config.BASE_PATH + Config.TEAMS_URL + "/" + teamId);
	
	    return results.getStatusCodeValue() == 204;
	}
	
}
