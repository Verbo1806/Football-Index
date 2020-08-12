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
import bg.verbo.footind.web.dto.PlayerDTO;
import bg.verbo.footind.web.dto.TeamDTO;
import bg.verbo.footind.web.service.PlayerService;
import bg.verbo.footind.web.service.TeamService;
import bg.verbo.footind.web.validator.DefaultValidator;
import bg.verbo.footind.web.vm.PlayersVM;

@VariableResolver(DelegatingVariableResolver.class)
public class AddEditPlayerVM {
	@Wire private Window addEditDlg;
	
	@WireVariable private PlayerService playerService;
	@WireVariable private TeamService teamService;

	private PlayersVM parent;
	private PlayerDTO addEditObject;
	private DefaultValidator<PlayerDTO> validator;
	
	private List<TeamDTO> teams;
	
	@Init
	@SuppressWarnings("unchecked")
	public void init() {
		Map<String, Object> args = (Map<String, Object>) Executions.getCurrent().getArg();
		parent = (PlayersVM) args.get(Config.PARENT_VM);
		addEditObject = (PlayerDTO) args.get(Config.PARAM);
		if (addEditObject == null) {
			addEditObject = new PlayerDTO();
		}
		validator = new DefaultValidator<>();
		
		teams = teamService.findAll();
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
							playerService.create(addEditObject);
						} else {
							playerService.update(addEditObject, addEditObject.getId());
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
	public PlayerDTO getAddEditObject() {
		return addEditObject;
	}

	public void setAddEditObject(PlayerDTO addEditObject) {
		this.addEditObject = addEditObject;
	}

	public List<TeamDTO> getTeams() {
		return teams;
	}
}
