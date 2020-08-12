package bg.verbo.footind.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class GroundDTO {
	private Long id;
	
	@NotBlank(message = "Name cannot be blank")
	@Size(max = 32, message = "Name length must be less than 32 characters")
	private String name;
	
	@PositiveOrZero(message = "Capacity must be a positive number or zero")
	private int capacity;
	
	@NotBlank(message = "Address cannot be blank")
	@Size(max = 1024, message = "Address length must be less than 1024 characters")
	private String address;
	
	@NotBlank(message = "Founded cannot be blank")
	@Size(max = 4, message = "Founded length must be less than 4 characters")
	private String founded;
	
	private StadiumCategoryDTO category;
	private SurfaceTypeDTO surface;
}
