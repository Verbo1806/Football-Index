package bg.verbo.footind.web.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import bg.verbo.footind.web.config.Config;
import bg.verbo.footind.web.dto.StadiumCategoryDTO;
import bg.verbo.footind.web.http.GenericRequests;
import bg.verbo.footind.web.util.Pair;
import bg.verbo.footind.web.util.QueryParamUtil;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StadiumCategoryService {
	
	private GenericRequests<StadiumCategoryDTO> requestBuilder;
	private ObjectMapper mapper;
	
	public List<StadiumCategoryDTO> findAll() {
		ResponseEntity<List<StadiumCategoryDTO>> results 
			= requestBuilder.findAll(Config.BASE_PATH + Config.STADIUM_CATEGORIES_URL);
		
	    if (results.hasBody()) {
	    	return mapper.convertValue(
	    			results.getBody(), 
	    		    new TypeReference<List<StadiumCategoryDTO>>(){}
	    		);
	    }
	    
	    return Collections.emptyList();
	}
	
	public List<StadiumCategoryDTO> findByExample(StadiumCategoryDTO category) {
		Map<String, String> params 
			= QueryParamUtil.build(new Pair<String, String>("code", category.getCode()), new Pair<String, String>("name", category.getName()));
		
		if (params.size() == 0) {
			return findAll();
		} else {
			ResponseEntity<List<StadiumCategoryDTO>> results 
				= requestBuilder.findByExample(Config.BASE_PATH + Config.STADIUM_CATEGORIES_URL  + "/search", params);
		
		    if (results.getStatusCodeValue() == 200) {
		    	return mapper.convertValue(
		    			results.getBody(), 
		    		    new TypeReference<List<StadiumCategoryDTO>>(){}
		    		);
		    } else {
		    	return Collections.emptyList();
		    }
		}
	}
	
}
