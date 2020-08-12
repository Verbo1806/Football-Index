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

import bg.verbo.footind.db.entity.SurfaceType;
import bg.verbo.footind.db.repository.SurfaceTypeRepository;
import bg.verbo.footind.dto.SurfaceTypeDTO;
import bg.verbo.footind.service.SurfaceTypeService;

@ExtendWith(MockitoExtension.class)
public class SurfaceTypeServiceTest {

	private SurfaceTypeService service;
	private SurfaceType surfaceType;
	private ArrayList<SurfaceType> allTypes;

	@Mock
	SurfaceTypeRepository mockSurfaceTypeRepository;

	@Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        service = new SurfaceTypeService(mockSurfaceTypeRepository);
        
        surfaceType = new SurfaceType();
        surfaceType.setCode("New code");
        surfaceType.setName("New name");
	    
        allTypes = new ArrayList<>();
        allTypes.add(surfaceType);
    }
	
	@Test
	public void testFindAll() {
	    when(mockSurfaceTypeRepository.findAll()).
	        thenReturn(allTypes);

	    List<SurfaceTypeDTO> surfaceTypeDTOs = service.findAll();

	    Assertions.assertEquals(1, surfaceTypeDTOs.size());
	    SurfaceTypeDTO actualDTO = surfaceTypeDTOs.get(0);

	    Assertions.assertEquals(surfaceType.getName(), actualDTO.getName());
	    Assertions.assertEquals(surfaceType.getCode(), actualDTO.getCode());
	}
	
}
