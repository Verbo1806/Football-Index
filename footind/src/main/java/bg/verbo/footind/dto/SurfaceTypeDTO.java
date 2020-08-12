package bg.verbo.footind.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SurfaceTypeDTO {
	@NotBlank(message = "Code cannot be blank")
	@Size(max = 4, message = "Code length must be less than 4 characters")
	private String code;
	
	@NotBlank(message = "Name cannot be blank")
	@Size(max = 32, message = "Name length must be less than 32 characters")
	private String name;
	
	@NotBlank(message = "Description cannot be blank")
	@Size(max = 1024, message = "Description length must be less than 1024 characters")
	private String description;
}
