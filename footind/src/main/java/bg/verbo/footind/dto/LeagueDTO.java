package bg.verbo.footind.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class LeagueDTO {
	private Long id;
	
	@NotBlank(message = "Name cannot be blank")
	@Size(max = 32, message = "Name length must be less than 32 characters")
	private String name;
	
	@NotBlank(message = "Founded cannot be blank")
	@Size(max = 4, message = "Founded length must be less than 4 characters")
	private String founded;
	
	private ConfederationDTO confederation;
	private CountryDTO country;
	private CompetitionTypeDTO type;
	
	@PositiveOrZero(message = "Number of teams must be a positive number or zero")
	private Short numberOfTeams;
	
	@PositiveOrZero(message = "Level on pyramid must be a positive number")
	private Short levelOnPyramid;
}
