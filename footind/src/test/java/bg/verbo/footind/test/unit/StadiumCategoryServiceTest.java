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

import bg.verbo.footind.db.entity.StadiumCategory;
import bg.verbo.footind.db.repository.StadiumCategoryRepository;
import bg.verbo.footind.dto.StadiumCategoryDTO;
import bg.verbo.footind.service.StadiumCategoryService;

@ExtendWith(MockitoExtension.class)
public class StadiumCategoryServiceTest {

	private StadiumCategoryService service;
	private StadiumCategory stadiumCategory;
	private ArrayList<StadiumCategory> allCategories;

	@Mock
	StadiumCategoryRepository mockStadiumCategoryRepository;

	@Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        service = new StadiumCategoryService(mockStadiumCategoryRepository);
        
        stadiumCategory = new StadiumCategory();
        stadiumCategory.setCode("New code");
        stadiumCategory.setName("New name");
	    
        allCategories = new ArrayList<>();
        allCategories.add(stadiumCategory);
    }
	
	@Test
	public void testFindAll() {
	    when(mockStadiumCategoryRepository.findAll()).
	        thenReturn(allCategories);

	    List<StadiumCategoryDTO> stadiumCategoryDTOs = service.findAll();

	    Assertions.assertEquals(1, stadiumCategoryDTOs.size());
	    StadiumCategoryDTO actualDTO = stadiumCategoryDTOs.get(0);

	    Assertions.assertEquals(stadiumCategory.getName(), actualDTO.getName());
	    Assertions.assertEquals(stadiumCategory.getCode(), actualDTO.getCode());
	}
	
}
