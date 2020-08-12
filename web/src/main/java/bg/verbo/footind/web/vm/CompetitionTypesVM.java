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

import bg.verbo.footind.web.dto.CompetitionTypeDTO;
import bg.verbo.footind.web.service.CompetitionTypeService;

@VariableResolver(DelegatingVariableResolver.class)
public class CompetitionTypesVM {
	@WireVariable private CompetitionTypeService competitionTypeService;

	private List<CompetitionTypeDTO> types;
	private CompetitionTypeDTO searchObject;
	
	@Init
	public void init() {
		types = competitionTypeService.findAll();
		searchObject = new CompetitionTypeDTO();
	}
	
	@AfterCompose
	public void afterComposeMenu(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
	}
	
	@Command
	@NotifyChange("types")
	public void search() {
		//countries = countryService.getCountriesByExample(searchObject);
	}
	
	@Command
	@NotifyChange({ "searchObject", "types" })
	public void clear() {
		searchObject = new CompetitionTypeDTO();
		//countries = countryService.getCountriesByExample(searchObject);
	}
	
	public void refresh() {
		types = competitionTypeService.findAll();
        BindUtils.postNotifyChange(null, null, this, "types");
	}

	/* Getters & Setters */
	public List<CompetitionTypeDTO> getTypes() {
		return types;
	}

	public CompetitionTypeDTO getSearchObject() {
		return searchObject;
	}

	public void setSearchObject(CompetitionTypeDTO searchObject) {
		this.searchObject = searchObject;
	}
}
