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

import bg.verbo.footind.web.dto.SurfaceTypeDTO;
import bg.verbo.footind.web.service.SurfaceService;

@VariableResolver(DelegatingVariableResolver.class)
public class SurfacesVM {
	@WireVariable private SurfaceService surfaceService;

	private List<SurfaceTypeDTO> surfaces;
	private SurfaceTypeDTO searchObject;
	
	@Init
	public void init() {
		surfaces = surfaceService.findAll();
		searchObject = new SurfaceTypeDTO();
	}
	
	@AfterCompose
	public void afterComposeMenu(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
	}
	
	@Command
	@NotifyChange("surfaces")
	public void search() {
		//countries = countryService.getCountriesByExample(searchObject);
	}
	
	@Command
	@NotifyChange({ "searchObject", "surfaces" })
	public void clear() {
		searchObject = new SurfaceTypeDTO();
		//countries = countryService.getCountriesByExample(searchObject);
	}
	
	public void refresh() {
		surfaces = surfaceService.findAll();
        BindUtils.postNotifyChange(null, null, this, "surfaces");
	}

	/* Getters & Setters */
	public List<SurfaceTypeDTO> getSurfaces() {
		return surfaces;
	}

	public SurfaceTypeDTO getSearchObject() {
		return searchObject;
	}

	public void setSearchObject(SurfaceTypeDTO searchObject) {
		this.searchObject = searchObject;
	}
}
