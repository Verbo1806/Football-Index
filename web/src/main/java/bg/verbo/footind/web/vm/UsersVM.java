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
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Window;

import bg.verbo.footind.web.config.Config;
import bg.verbo.footind.web.dto.TeamDTO;
import bg.verbo.footind.web.dto.auth.UserPrincipal;
import bg.verbo.footind.web.service.UserService;

@VariableResolver(DelegatingVariableResolver.class)
public class UsersVM {
	@WireVariable private UserService userService;

	private List<UserPrincipal> users;
	
	@Init
	public void init() {
		users = userService.findAll();
	}
	
	@AfterCompose
	public void afterComposeMenu(@ContextParam(ContextType.VIEW) Component view){
		Selectors.wireComponents(view, this, false);
	}
	
	@Command
	public void addEdit(@BindingParam("object") UserPrincipal object) {
		Map<String, Object> params = new HashMap<>();
		params.put(Config.PARENT_VM, this);
		params.put(Config.PARAM, object);
		
		Window window = (Window) Executions.createComponents("~./zul/edit/editUser.zul", null, params);
		window.doModal();
	}
	
	public void refresh() {
		users = userService.findAll();
        BindUtils.postNotifyChange(null, null, this, "users");
	}

	/* Getters & Setters */
	public List<UserPrincipal> getUsers() {
		return users;
	}
}
