package bg.verbo.footind.web.security;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import bg.verbo.footind.web.config.Config;
import bg.verbo.footind.web.dto.auth.AuthRequest;
import bg.verbo.footind.web.dto.auth.AuthResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private RestTemplate template;
	
    @Override
    public Authentication authenticate(Authentication authentication) {
    	AuthRequest request = new AuthRequest(authentication.getName(), authentication.getCredentials().toString());
    	HttpEntity<AuthRequest> entity = new HttpEntity<>(request, null);
    	ResponseEntity<AuthResponse> response = null;
    	try {
    		response = template.exchange(
    				Config.BASE_PATH + Config.AUTHENTICATION_URL,
    		        HttpMethod.POST,
    		        entity,
    		        new ParameterizedTypeReference<AuthResponse>() {});
    		
    		return new UsernamePasswordAuthenticationToken(
      				 response.getBody().getUser().getUsername(),
      				 response.getBody().getJwtToken(),
      				 response.getBody().getUser().getAuthorities());
    		
    	} catch (HttpStatusCodeException e) {
    		throw new BadCredentialsException(e.getStatusText());
    	}
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
