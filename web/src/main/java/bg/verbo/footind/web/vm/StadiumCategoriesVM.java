package bg.verbo.footind.web.vm;

import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import bg.verbo.footind.web.dto.StadiumCategoryDTO;
import bg.verbo.footind.web.service.StadiumCategoryService;

@VariableResolver(DelegatingVariableResolver.class)
public class StadiumCategoriesVM {
	@WireVariable private StadiumCategoryService stadiumCategoryService;

	private List<StadiumCategoryDTO> categories;
	private StadiumCategoryDTO searchObject;
	
	@Init
	public void init() {
		categories = stadiumCategoryService.findAll();
		searchObject = new StadiumCategoryDTO();
	}
	
	@AfterCompose
	public void afterComposeMenu(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
	}
	
	@Command
	@NotifyChange("categories")
	public void search() {
		categories = stadiumCategoryService.findByExample(searchObject);
	}
	
	@Command
	@NotifyChange({ "searchObject", "categories" })
	public void clear() {
		searchObject = new StadiumCategoryDTO();
		categories = stadiumCategoryService.findByExample(searchObject);
	}
	
	public void refresh() {
		categories = stadiumCategoryService.findAll();
        BindUtils.postNotifyChange(null, null, this, "categories");
	}

	/* Getters & Setters */
	public List<StadiumCategoryDTO> getCategories() {
		return categories;
	}

	public StadiumCategoryDTO getSearchObject() {
		return searchObject;
	}

	public void setSearchObject(StadiumCategoryDTO searchObject) {
		this.searchObject = searchObject;
	}
}
