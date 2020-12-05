package bg.verbo.footind.web.http;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import bg.verbo.footind.web.config.Config;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class GenericRequests<T> {
	
	private final RestTemplate template;
	private final Class<T> modelClass;

	public ResponseEntity<List<T>> findAll(String url) {
		HttpHeaders headers = new HttpHeaders();
	    headers.add(Config.AUTHORIZATION_HEADER, Config.JWT_AUTH + getToken());
		
		return template.exchange(
				url,
		        HttpMethod.GET,
		        new HttpEntity<>(null, headers),
		        new ParameterizedTypeReference<List<T>>() {
					@Override
					public Type getType() {
						return modelClass;
					}
				}
		    );
	}
	
	public ResponseEntity<T> findById(String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(Config.AUTHORIZATION_HEADER, Config.JWT_AUTH + getToken());
		
		return template.exchange(
		        url,
		        HttpMethod.GET,
		        new HttpEntity<>(null, headers),
		        new ParameterizedTypeReference<T>() {}
		    );
	}
	
	public ResponseEntity<List<T>> findByExample(String url, Map<String, String> params) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(Config.AUTHORIZATION_HEADER, Config.JWT_AUTH + getToken());
	    
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		
		for (Map.Entry<String, String> entry : params.entrySet()) {
			builder.queryParam(entry.getKey(), entry.getValue());
		}

		return template.exchange(
				builder.toUriString(),
		        HttpMethod.GET,
		        new HttpEntity<>(null, headers),
		        new ParameterizedTypeReference<List<T>>() {}
		    );
	}
	
	public ResponseEntity<T> create(String url, T dto) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(Config.AUTHORIZATION_HEADER, Config.JWT_AUTH + getToken());
		
		HttpEntity<T> entity = new HttpEntity<>(dto, headers);
		
		return template.exchange(
		        url,
		        HttpMethod.POST,
		        entity,
		        new ParameterizedTypeReference<T>() {}
		    );
	}
	
	public ResponseEntity<T> update(String url, T dto) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(Config.AUTHORIZATION_HEADER, Config.JWT_AUTH + getToken());
		
		HttpEntity<T> entity = new HttpEntity<>(dto, headers);
		
		return template.exchange(
		        url,
		        HttpMethod.PUT,
		        entity,
		        new ParameterizedTypeReference<T>() {}
		    );
	}
	
	public ResponseEntity<T> delete(String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(Config.AUTHORIZATION_HEADER, Config.JWT_AUTH + getToken());
	    
		return template.exchange(
		        url,
		        HttpMethod.DELETE,
		        new HttpEntity<>(null, headers),
		        new ParameterizedTypeReference<T>() {}
		    );
	}
	
	private String getToken() {
		return SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
	}
}
