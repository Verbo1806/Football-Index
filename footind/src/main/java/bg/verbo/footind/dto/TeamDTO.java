package bg.verbo.footind.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class TeamDTO {
	private Long id;
	
	@NotBlank(message = "Name cannot be blank")
	@Size(max = 32, message = "Name length must be less than 32 characters")
	private String name;
	
	@NotBlank(message = "Abbreaviature cannot be blank")
	@Size(max = 4, message = "Abbreaviature length must be less than 4 characters")
	private String abbreaviature;

	@NotBlank(message = "Founded cannot be blank")
	@Size(max = 4, message = "Founded length must be less than 4 characters")
	private String founded;
	
	private GroundDTO ground;
	private LeagueDTO league;
	private List<PlayerDTO> players;
}
