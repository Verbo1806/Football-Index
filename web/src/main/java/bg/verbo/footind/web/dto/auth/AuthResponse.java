package bg.verbo.footind.web.dto.auth;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse implements Serializable {
	private static final long serialVersionUID = 5022733775368733609L;

	private UserPrincipal user;
	private String jwtToken;
}
