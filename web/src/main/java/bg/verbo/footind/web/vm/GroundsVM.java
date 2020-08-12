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
import bg.verbo.footind.web.dto.GroundDTO;
import bg.verbo.footind.web.service.GroundService;

@VariableResolver(DelegatingVariableResolver.class)
public class GroundsVM {
	@WireVariable private GroundService groundService;

	private List<GroundDTO> grounds;
	private GroundDTO searchObject;
	
	@Init
	public void init() {
		grounds = groundService.findAll();
		searchObject = new GroundDTO();
	}
	
	@AfterCompose
	public void afterComposeMenu(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
	}
	
	@Command
	@NotifyChange("grounds")
	public void search() {
		grounds = groundService.findByExample(searchObject);
	}
	
	@Command
	@NotifyChange({ "searchObject", "grounds" })
	public void clear() {
		searchObject = new GroundDTO();
		//countries = countryService.getCountriesByExample(searchObject);
	}
	
	@Command
	public void addEdit(@BindingParam("object") GroundDTO object) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(Config.PARENT_VM, this);
		params.put(Config.PARAM, object);
		
		Window window = (Window) Executions.createComponents("~./zul/edit/addEditGround.zul", null, params);
		window.doModal();
	}
	
	@Command
	public void delete(@BindingParam("id") Long id) {
		if (groundService.delete(id)) {
			Messagebox.show(Labels.getLabel("validation.delete"), Labels.getLabel("validation.confirmOperation"),
				new Messagebox.Button[] {Messagebox.Button.YES, Messagebox.Button.NO},
				new String[] {Labels.getLabel("yes"), Labels.getLabel("no")},
				Messagebox.QUESTION, null, e -> {
					if (Messagebox.ON_YES.equals(e.getName())) {
						Messagebox.show(Labels.getLabel("validation.entityDeletedSuccessfully"), 
								Labels.getLabel("successfulOperation"), Messagebox.OK, Messagebox.INFORMATION);
						grounds.removeIf(obj -> obj.getId().equals(id));
						BindUtils.postNotifyChange(null, null, this, "grounds");
					}
				}
			);
		}
	}
	
	public void refresh() {
		grounds = groundService.findAll();
        BindUtils.postNotifyChange(null, null, this, "grounds");
	}

	/* Getters & Setters */
	public List<GroundDTO> getGrounds() {
		return grounds;
	}

	public GroundDTO getSearchObject() {
		return searchObject;
	}

	public void setSearchObject(GroundDTO searchObject) {
		this.searchObject = searchObject;
	}
}
