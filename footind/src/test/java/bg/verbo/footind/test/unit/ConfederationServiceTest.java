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

import bg.verbo.footind.db.entity.Confederation;
import bg.verbo.footind.db.repository.ConfederationRepository;
import bg.verbo.footind.dto.ConfederationDTO;
import bg.verbo.footind.service.ConfederationService;
import bg.verbo.footind.service.mapper.ConfederationMapper;

@ExtendWith(MockitoExtension.class)
public class ConfederationServiceTest {

	private ConfederationService service;
	private Confederation confederation;
	private ArrayList<Confederation> allConfederations;

	@Mock
	ConfederationRepository mockConfederationRepository;

	@Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        service = new ConfederationService(mockConfederationRepository);
        
        confederation = new Confederation();
        confederation.setId((long) 160);
        confederation.setName("New confederation");
	    confederation.setFounded("2020");
	    
	    allConfederations = new ArrayList<>();
	    allConfederations.add(confederation);
    }
	
	@Test
	public void testFindAll() {
	    when(mockConfederationRepository.findAll()).
	        thenReturn(allConfederations);

	    List<ConfederationDTO> confederationDTOs = service.findAll();

	    Assertions.assertEquals(1, confederationDTOs.size());
	    ConfederationDTO actualDTO = confederationDTOs.get(0);

	    Assertions.assertEquals(confederation.getName(), actualDTO.getName());
	    Assertions.assertEquals(confederation.getFounded(), actualDTO.getFounded());
	}
	
	@Test
	public void testCreate() {
		Confederation newConfederation = new Confederation();
		newConfederation.setName("2");
		newConfederation.setFounded("2020");
	    
		when(mockConfederationRepository.save(newConfederation))
        	.thenReturn(newConfederation);
		
		ConfederationDTO testConfederation = service.save(ConfederationMapper.INSTANCE.mapConfederationEntityToDto(newConfederation));
		allConfederations.add(newConfederation);
		Assertions.assertEquals(2, allConfederations.size());
		
		Assertions.assertEquals(testConfederation.getName(), newConfederation.getName());
	    Assertions.assertEquals(testConfederation.getFounded(), newConfederation.getFounded());
	}
	
	@Test
	public void testUpdate() {
		when(mockConfederationRepository.save(confederation))
        	.thenReturn(confederation);
		
		confederation.setFounded("2019");
		ConfederationDTO updatedConfederation = service.save(ConfederationMapper.INSTANCE.mapConfederationEntityToDto(confederation));

		Assertions.assertEquals(updatedConfederation.getName(), confederation.getName());
	    Assertions.assertEquals(updatedConfederation.getFounded(), confederation.getFounded());
	}

}
