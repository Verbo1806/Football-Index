package bg.verbo.footind.web.vm.edit;

import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
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
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bg.verbo.footind.web.config.Config;
import bg.verbo.footind.web.dto.auth.AuthorityDTO;
import bg.verbo.footind.web.dto.auth.UserPrincipal;
import bg.verbo.footind.web.service.AuthorityService;
import bg.verbo.footind.web.validator.DefaultValidator;
import bg.verbo.footind.web.vm.UsersVM;

@VariableResolver(DelegatingVariableResolver.class)
public class EditUserVM {
	@Wire private Window addEditDlg;
	
	@WireVariable private AuthorityService authorityService;

	private UsersVM parent;
	private UserPrincipal addEditObject;
	private DefaultValidator<UserPrincipal> validator;
	
	private List<AuthorityDTO> authorities;

	@Init
	@SuppressWarnings("unchecked")
	public void init() {
		Map<String, Object> args = (Map<String, Object>) Executions.getCurrent().getArg();
		parent = (UsersVM) args.get(Config.PARENT_VM);
		addEditObject = (UserPrincipal) args.get(Config.PARAM);
		if (addEditObject == null) {
			addEditObject = new UserPrincipal();
		}
		validator = new DefaultValidator<>();
		
		authorities = authorityService.findAll();
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	@Command
	public void save() {
		/*if (!validator.isValid(addEditObject)) {
			Messagebox.show(Labels.getLabel("validation.fillAllMandatoryFields"), Labels.getLabel("error"), Messagebox.OK, Messagebox.ERROR);
		} else {
			Messagebox.show(Labels.getLabel("validation.save"), Labels.getLabel("validation.confirmOperation"),
				new Messagebox.Button[] {Messagebox.Button.YES, Messagebox.Button.NO},
				new String[] {Labels.getLabel("yes"), Labels.getLabel("no")},
				Messagebox.QUESTION, null, e -> {
					if (Messagebox.ON_YES.equals(e.getName())) {
						if (addEditObject.getId() == null) {
							teamService.create(addEditObject);
						} else {
							teamService.update(addEditObject, addEditObject.getId());
						}
						parent.refresh();
						addEditDlg.detach();
					}
				}
			);
		}*/
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
	
	@Command
	@NotifyChange("displaySelectedAuthorities")
	public void selectAuthority() {}
	
	public String displaySelectedAuthorities() {
		if (addEditObject.getAuthorities().isEmpty()) {
			return Labels.getLabel("noSelectedAuthorities");
		} else if (addEditObject.getAuthorities().size() == 1) {
			return Labels.getLabel("oneSelectedAuthority");
		} else {
			return Labels.getLabel("manySelectedAuthorities", new Object[] {addEditObject.getAuthorities().size()});
		}
	}

	/* Getters & Setters */
	public UserPrincipal getAddEditObject() {
		return addEditObject;
	}

	public void setAddEditObject(UserPrincipal addEditObject) {
		this.addEditObject = addEditObject;
	}

	public List<AuthorityDTO> getAuthorities() {
		return authorities;
	}
}
