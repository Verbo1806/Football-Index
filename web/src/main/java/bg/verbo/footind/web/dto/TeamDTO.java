package bg.verbo.footind.web.dto;

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
	
	@NotBlank
	@Size(max = 32)
	private String name;
	
	@NotBlank
	@Size(max = 4)
	private String abbreaviature;
	
	@NotBlank
	@Size(max = 4)
	private String founded;
	
	private GroundDTO ground;
	private LeagueDTO league;
	private List<PlayerDTO> players;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TeamDTO other = (TeamDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
