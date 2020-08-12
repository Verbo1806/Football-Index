package bg.verbo.footind.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bg.verbo.footind.config.ControllerConfig;
import bg.verbo.footind.dto.auth.AuthRequest;
import bg.verbo.footind.dto.auth.AuthResponse;
import bg.verbo.footind.dto.auth.UserPrincipal;
import bg.verbo.footind.service.UserService;
import bg.verbo.footind.util.JwtTokenUtil;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping(ControllerConfig.AUTHENTICATION_URL)
public class AuthenticationController {
	private AuthenticationManager authenticationManager;
	
	private JwtTokenUtil jwtTokenUtil;
	
	private UserService userService;

	@GetMapping("/test")
	public String test(Authentication authentication) {
		return "Da";
	}
	
	@GetMapping("/users")
	public List<UserDetails> findAll() {
		return userService.findAllUsers();
	}
	
	@PostMapping
	public ResponseEntity<AuthResponse> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest) {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserPrincipal userDetails = (UserPrincipal) userService
				.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthResponse(userDetails, token));
	}
	
	private void authenticate(String username, String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new DisabledException("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("INVALID_CREDENTIALS", e);
		}
	}

}
