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

import bg.verbo.footind.db.entity.Ground;
import bg.verbo.footind.db.repository.GroundRepository;
import bg.verbo.footind.dto.GroundDTO;
import bg.verbo.footind.service.GroundService;
import bg.verbo.footind.service.mapper.GroundMapper;

@ExtendWith(MockitoExtension.class)
public class GroundServiceTest {

	private GroundService service;
	private Ground ground;
	private ArrayList<Ground> allGrounds;

	@Mock
	GroundRepository mockGroundRepository;

	@Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        service = new GroundService(mockGroundRepository);
        
        ground = new Ground();
        ground.setId((long) 160);
        ground.setName("New ground");
	    ground.setFounded("2020");
	    
	    allGrounds = new ArrayList<>();
	    allGrounds.add(ground);
    }
	
	@Test
	public void testFindAll() {
	    when(mockGroundRepository.findAll()).
	        thenReturn(allGrounds);

	    List<GroundDTO> groundDTOs = service.findAll();

	    Assertions.assertEquals(1, groundDTOs.size());
	    GroundDTO actualDTO = groundDTOs.get(0);

	    Assertions.assertEquals(ground.getName(), actualDTO.getName());
	    Assertions.assertEquals(ground.getFounded(), actualDTO.getFounded());
	}
	
	@Test
	public void testCreate() {
		Ground newGround = new Ground();
		newGround.setName("2");
		newGround.setFounded("2020");
	    
		when(mockGroundRepository.save(newGround))
        	.thenReturn(newGround);
		
		GroundDTO testGround = service.save(GroundMapper.INSTANCE.mapGroundEntityToDto(newGround));
		allGrounds.add(newGround);
		Assertions.assertEquals(2, allGrounds.size());
		
		Assertions.assertEquals(testGround.getName(), newGround.getName());
	    Assertions.assertEquals(testGround.getFounded(), newGround.getFounded());
	}
	
	@Test
	public void testUpdate() {
		when(mockGroundRepository.save(ground))
        	.thenReturn(ground);
		
		ground.setFounded("2019");
		GroundDTO updatedGround = service.save(GroundMapper.INSTANCE.mapGroundEntityToDto(ground));

		Assertions.assertEquals(updatedGround.getName(), ground.getName());
	    Assertions.assertEquals(updatedGround.getFounded(), ground.getFounded());
	}

}
