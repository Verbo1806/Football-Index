package bg.verbo.footind.web.dto;

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
	
	@NotBlank
	@Size(max = 32)
	private String name;
	
	@PositiveOrZero
	private int capacity;
	
	@NotBlank
	@Size(max = 1024)
	private String address;
	
	@NotBlank
	@Size(max = 4)
	private String founded;
	
	private StadiumCategoryDTO category;
	private SurfaceTypeDTO surface;
	
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
		GroundDTO other = (GroundDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
