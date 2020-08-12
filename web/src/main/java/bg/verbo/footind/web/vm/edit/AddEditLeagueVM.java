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
import bg.verbo.footind.web.dto.CompetitionTypeDTO;
import bg.verbo.footind.web.dto.ConfederationDTO;
import bg.verbo.footind.web.dto.CountryDTO;
import bg.verbo.footind.web.dto.LeagueDTO;
import bg.verbo.footind.web.service.CompetitionTypeService;
import bg.verbo.footind.web.service.ConfederationService;
import bg.verbo.footind.web.service.CountryService;
import bg.verbo.footind.web.service.LeagueService;
import bg.verbo.footind.web.validator.DefaultValidator;
import bg.verbo.footind.web.vm.LeaguesVM;

@VariableResolver(DelegatingVariableResolver.class)
public class AddEditLeagueVM {
	@Wire private Window addEditDlg;
	
	@WireVariable private CompetitionTypeService competitionTypeService;
	@WireVariable private ConfederationService confederationService;
	@WireVariable private CountryService countryService;
	@WireVariable private LeagueService leagueService;

	private LeaguesVM parent;
	private LeagueDTO addEditObject;
	private DefaultValidator<LeagueDTO> validator;
	
	private List<CompetitionTypeDTO> types;
	private List<ConfederationDTO> confederations;
	private List<CountryDTO> countries;

	@Init
	@SuppressWarnings("unchecked")
	public void init() {
		Map<String, Object> args = (Map<String, Object>) Executions.getCurrent().getArg();
		parent = (LeaguesVM) args.get(Config.PARENT_VM);
		addEditObject = (LeagueDTO) args.get(Config.PARAM);
		if (addEditObject == null) {
			addEditObject = new LeagueDTO();
		}
		validator = new DefaultValidator<>();
		
		confederations = confederationService.findAll();
		types = competitionTypeService.findAll();
		countries = countryService.findAll();
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
							leagueService.create(addEditObject);
						} else {
							leagueService.update(addEditObject, addEditObject.getId());
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
	public LeagueDTO getAddEditObject() {
		return addEditObject;
	}

	public void setAddEditObject(LeagueDTO addEditObject) {
		this.addEditObject = addEditObject;
	}

	public List<CompetitionTypeDTO> getTypes() {
		return types;
	}

	public List<ConfederationDTO> getConfederations() {
		return confederations;
	}

	public List<CountryDTO> getCountries() {
		return countries;
	}
}
