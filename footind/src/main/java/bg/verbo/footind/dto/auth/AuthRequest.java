package bg.verbo.footind.dto.auth;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest implements Serializable {
	private static final long serialVersionUID = 5544040861400612445L;
	
	private String username;
	private String password;
}
