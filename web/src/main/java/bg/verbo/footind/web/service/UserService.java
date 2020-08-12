package bg.verbo.footind.web.service;

import java.util.Collections;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import bg.verbo.footind.web.config.Config;
import bg.verbo.footind.web.dto.auth.UserPrincipal;
import bg.verbo.footind.web.http.GenericRequests;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {
	
	private GenericRequests<UserPrincipal> requestBuilder;
	private ObjectMapper mapper;
	
	public List<UserPrincipal> findAll() {
		ResponseEntity<List<UserPrincipal>> results 
			= requestBuilder.findAll(Config.BASE_PATH + Config.AUTHENTICATION_URL + "/users");
	
	    if (results.hasBody()) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<List<UserPrincipal>>(){}
	    		);
	    }
	    
	    return Collections.emptyList();
	}
	
}
