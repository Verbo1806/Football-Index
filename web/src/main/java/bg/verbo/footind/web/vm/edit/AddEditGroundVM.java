package bg.verbo.footind.web.vm.edit;

import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bg.verbo.footind.web.config.Config;
import bg.verbo.footind.web.dto.GroundDTO;
import bg.verbo.footind.web.dto.StadiumCategoryDTO;
import bg.verbo.footind.web.dto.SurfaceTypeDTO;
import bg.verbo.footind.web.service.GroundService;
import bg.verbo.footind.web.service.StadiumCategoryService;
import bg.verbo.footind.web.service.SurfaceService;
import bg.verbo.footind.web.validator.DefaultValidator;
import bg.verbo.footind.web.vm.GroundsVM;

@VariableResolver(DelegatingVariableResolver.class)
public class AddEditGroundVM {
	@Wire private Window addEditDlg;
	
	@WireVariable private GroundService groundService;
	@WireVariable private StadiumCategoryService stadiumCategoryService;
	@WireVariable private SurfaceService surfaceService;

	private GroundsVM parent;
	private GroundDTO addEditObject;
	private DefaultValidator<GroundDTO> validator;
	
	private List<StadiumCategoryDTO> categories;
	private List<SurfaceTypeDTO> surfaces;
	
	@Init
	@SuppressWarnings("unchecked")
	public void init() {
		Map<String, Object> args = (Map<String, Object>) Executions.getCurrent().getArg();
		parent = (GroundsVM) args.get(Config.PARENT_VM);
		addEditObject = (GroundDTO) args.get(Config.PARAM);
		if (addEditObject == null) {
			addEditObject = new GroundDTO();
		}
		validator = new DefaultValidator<>();
		
		categories = stadiumCategoryService.findAll();
		surfaces = surfaceService.findAll();
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	@Command
	public void save() {
		if (!validator.isValid(addEditObject)) {
			Messagebox.show(Labels.getLabel("validation.fillAllMandatoryFields"), Labels.getLabel("error"), Messagebox.OK, Messagebox.ERROR);
		} else {
			Messagebox.show(Labels.getLabel("validation.save"), Labels.getLabel("validation.confirmOperation"),
				new Messagebox.Button[] {Messagebox.Button.YES, Messagebox.Button.NO},
				new String[] {Labels.getLabel("yes"), Labels.getLabel("no")},
				Messagebox.QUESTION, null, e -> {
					if (Messagebox.ON_YES.equals(e.getName())) {
						if (addEditObject.getId() == null) {
							groundService.create(addEditObject);
						} else {
							groundService.update(addEditObject, addEditObject.getId());
						}
						parent.refresh();
						addEditDlg.detach();
					}
				}
			);
		}
	}

	@Command
	public void cancel() {
		Messagebox.show(Labels.getLabel("validation.cancel"), Labels.getLabel("validation.confirmOperation"),
			new Messagebox.Button[] {Messagebox.Button.YES, Messagebox.Button.NO},
			new String[] {Labels.getLabel("yes"), Labels.getLabel("no")},
			Messagebox.QUESTION, null, e -> {
				if (Messagebox.ON_YES.equals(e.getName())) {
					parent.refresh();
					addEditDlg.detach();
				}
			}
		);
	}
	
	/* Getters & Setters */
	public GroundDTO getAddEditObject() {
		return addEditObject;
	}

	public void setAddEditObject(GroundDTO addEditObject) {
		this.addEditObject = addEditObject;
	}

	public List<StadiumCategoryDTO> getCategories() {
		return categories;
	}

	public List<SurfaceTypeDTO> getSurfaces() {
		return surfaces;
	}
}
