package bg.verbo.footind.web.vm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bg.verbo.footind.web.config.Config;
import bg.verbo.footind.web.dto.ConfederationDTO;
import bg.verbo.footind.web.service.ConfederationService;

@VariableResolver(DelegatingVariableResolver.class)
public class ConfederationsVM {
	@WireVariable private ConfederationService confederationService;

	private List<ConfederationDTO> confederations;
	private ConfederationDTO searchObject;
	
	@Init
	public void init() {
		confederations = confederationService.findAll();
		searchObject = new ConfederationDTO();
	}
	
	@AfterCompose
	public void afterComposeMenu(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
	}
	
	@Command
	@NotifyChange("confederations")
	public void search() {
		confederations = confederationService.findByExample(searchObject);
	}
	
	@Command
	@NotifyChange({ "searchObject", "confederations" })
	public void clear() {
		searchObject = new ConfederationDTO();
		//countries = countryService.getCountriesByExample(searchObject);
	}
	
	@Command
	public void addEdit(@BindingParam("object") ConfederationDTO object) {
		Map<String, Object> params = new HashMap<>();
		params.put(Config.PARENT_VM, this);
		params.put(Config.PARAM, object);
		
		Window window = (Window) Executions.createComponents("~./zul/edit/addEditConfederation.zul", null, params);
		window.doModal();
	}
	
	@Command
	public void delete(@BindingParam("id") Long id) {
		if (confederationService.delete(id)) {
			Messagebox.show(Labels.getLabel("validation.delete"), Labels.getLabel("validation.confirmOperation"),
				new Messagebox.Button[] {Messagebox.Button.YES, Messagebox.Button.NO},
				new String[] {Labels.getLabel("yes"), Labels.getLabel("no")},
				Messagebox.QUESTION, null, e -> {
					if (Messagebox.ON_YES.equals(e.getName())) {
						Messagebox.show(Labels.getLabel("validation.entityDeletedSuccessfully"), 
								Labels.getLabel("successfulOperation"), Messagebox.OK, Messagebox.INFORMATION);
						confederations.removeIf(obj -> obj.getId().equals(id));
						BindUtils.postNotifyChange(null, null, this, "confederations");
					}
				}
			);
		}
	}
	
	public void refresh() {
		confederations = confederationService.findAll();
        BindUtils.postNotifyChange(null, null, this, "confederations");
	}

	/* Getters & Setters */
	public List<ConfederationDTO> getConfederations() {
		return confederations;
	}

	public ConfederationDTO getSearchObject() {
		return searchObject;
	}

	public void setSearchObject(ConfederationDTO searchObject) {
		this.searchObject = searchObject;
	}
}
