package bg.verbo.footind.web.vm;

import org.zkoss.bind.annotation.MatchMedia;
import org.zkoss.zul.Messagebox;

public class AbstractVM {

    @MatchMedia("all and (max-width: 500px)")
    public void handleMax500() {
       Messagebox.show("Are you sure you want to switch to desktop?", "Warning", Messagebox.OK, Messagebox.EXCLAMATION, null);
    }

    @MatchMedia("all and (min-width: 501px)")
    public void handleMin500() {
       Messagebox.show("Are you sure you want to switch to desktop?", "Warning", Messagebox.OK, Messagebox.EXCLAMATION, null);
    }
        
}
