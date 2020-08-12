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

import bg.verbo.footind.web.dto.CountryDTO;
import bg.verbo.footind.web.service.CountryService;

@VariableResolver(DelegatingVariableResolver.class)
public class CountriesVM {
	@WireVariable private CountryService countryService;

	private List<CountryDTO> countries;
	private CountryDTO searchObject;
	
	@Init
	public void init() {
		countries = countryService.findAll();
		searchObject = new CountryDTO();
	}
	
	@AfterCompose
	public void afterComposeMenu(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
	}
	
	@Command
	@NotifyChange("countries")
	public void search() {
		//countries = countryService.getCountriesByExample(searchObject);
	}
	
	@Command
	@NotifyChange({ "searchObject", "countries" })
	public void clear() {
		searchObject = new CountryDTO();
		//countries = countryService.getCountriesByExample(searchObject);
	}
	
	public void refresh() {
		countries = countryService.findAll();
        BindUtils.postNotifyChange(null, null, this, "countries");
	}

	/* Getters & Setters */
	public List<CountryDTO> getCountries() {
		return countries;
	}

	public CountryDTO getSearchObject() {
		return searchObject;
	}

	public void setSearchObject(CountryDTO searchObject) {
		this.searchObject = searchObject;
	}
}
