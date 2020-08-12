package bg.verbo.footind.dto.auth;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse implements Serializable {
	private static final long serialVersionUID = 5022733775368733609L;

	private final UserPrincipal user;
	private final String jwtToken;
}
