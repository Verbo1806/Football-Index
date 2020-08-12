package bg.verbo.footind.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ConfederationDTO {
	private Long id;
	
	@NotBlank(message = "Name cannot be blank")
	@Size(max = 32, message = "Name length must be less than 32 characters")
	private String name;
	
	@NotBlank(message = "Founded cannot be blank")
	@Size(max = 4, message = "Founded length must be less than 4 characters")
	private String founded;
	
	@NotBlank(message = "Headquarters cannot be blank")
	@Size(max = 64, message = "Headquarters length must be less than 64 characters")
	private String headquarters;
}
