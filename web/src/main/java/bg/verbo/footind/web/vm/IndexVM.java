package bg.verbo.footind.web.vm;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;

import bg.verbo.footind.web.config.Config;

public class IndexVM {
	@Wire private Component centerContent;
	
	private String deviceType;
	
	@Init
	public void init() {
		if (Executions.getCurrent().getBrowser("mobile") != null) {
			deviceType = "mobile";
		} else {
			deviceType = "desktop";
		}
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	@GlobalCommand
	public void changeCenterContent(@BindingParam("zulName") String zulName, @BindingParam("param") Object param) {
		// Detach center content
		if (centerContent != null) {
			centerContent.getChildren().clear();
		}
		
		Map<String, Object> params = new HashMap<>();
		if (param != null) {
			params.put(Config.PARAM, param);
		}
		
		if (deviceType.equals("desktop")) {
			Executions.createComponents("~./zul/" + zulName + ".zul", centerContent, params);
		} else {
			Executions.createComponents("~./zul/" + zulName + "_mobile.zul", centerContent, params);
		}
	}
	
	@Command
	public void logout() {
		Executions.sendRedirect("/logout");
	}
	
	public String getProfileName( ) {
		return Labels.getLabel("profileName", new Object[] {SecurityContextHolder.getContext().getAuthentication().getName()});
	}
	
	public String getDeviceType() {
		return deviceType;
	}
}
