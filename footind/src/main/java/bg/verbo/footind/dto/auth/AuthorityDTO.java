package bg.verbo.footind.dto.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class AuthorityDTO implements GrantedAuthority {
	private static final long serialVersionUID = 2283424763024374121L;

	private Long id;
	
	@NotBlank(message = "Authority cannot be blank")
	@Size(max = 32, message = "Authority length must be less than 32 characters")
	private String authority;

	@Override
	public String getAuthority() {
		return authority;
	}
}
