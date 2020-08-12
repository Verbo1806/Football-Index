package bg.verbo.footind.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class PlayerDTO {
	@ApiModelProperty(
			  value = "Unique identifier of the player",
			  name = "id",
			  dataType = "Long",
			  example = "1806")
    private Long id;
	
	@ApiModelProperty(
			  value = "Shirt number",
			  name = "shirtNumber",
			  dataType = "Short",
			  example = "9")
	@PositiveOrZero(message = "Shirt number must be a positive number or zero")
    private Short shirtNumber;
	
	@ApiModelProperty(
			  value = "Full name",
			  name = "fullName",
			  dataType = "String",
			  example = "Garra Dembele")
	@NotBlank(message = "Full name cannot be blank")
	@Size(max = 64, message = "Full name length must be less than 64 characters")
    private String fullName;
	
	@ApiModelProperty(
			  value = "Date of birth",
			  name = "bornAt",
			  dataType = "Date",
			  example = "1986-02-21 00:00:00")
	@Past(message = "Born at must be a date in the past")
    private Date bornAt;
	
    private TeamDTO team;
}
