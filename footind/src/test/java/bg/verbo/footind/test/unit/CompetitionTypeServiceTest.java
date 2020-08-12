package bg.verbo.footind.test.unit;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import bg.verbo.footind.db.entity.CompetitionType;
import bg.verbo.footind.db.repository.CompetitionTypeRepository;
import bg.verbo.footind.dto.CompetitionTypeDTO;
import bg.verbo.footind.service.CompetitionTypeService;

@ExtendWith(MockitoExtension.class)
public class CompetitionTypeServiceTest {

	private CompetitionTypeService service;
	private CompetitionType competitionType;
	private ArrayList<CompetitionType> allCompetitionTypes;

	@Mock
	CompetitionTypeRepository mockCompetitionTypeRepository;

	@Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        service = new CompetitionTypeService(mockCompetitionTypeRepository);
        
        competitionType = new CompetitionType();
        competitionType.setCode("New code");
        competitionType.setName("New name");
	    
	    allCompetitionTypes = new ArrayList<>();
	    allCompetitionTypes.add(competitionType);
    }
	
	@Test
	public void testFindAll() {
	    when(mockCompetitionTypeRepository.findAll()).
	        thenReturn(allCompetitionTypes);

	    List<CompetitionTypeDTO> competitionTypeDTOs = service.findAll();

	    Assertions.assertEquals(1, competitionTypeDTOs.size());
	    CompetitionTypeDTO actualDTO = competitionTypeDTOs.get(0);

	    Assertions.assertEquals(competitionType.getName(), actualDTO.getName());
	    Assertions.assertEquals(competitionType.getCode(), actualDTO.getCode());
	}
	
}
