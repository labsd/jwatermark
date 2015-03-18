package org.gen;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import org.gen.mvc.main.MainCtlr;
import org.gen.mvc.main.MainModl;
import org.gen.mvc.main.MainVw;

public class Fcty {

	private MainCtlr mainController;
	private MainVw mainView;
	private MainModl mainModel;
	private Locale locale = Locale.JAPANESE; 
			public MainCtlr getMainCtl() {
		return mainController != null ? mainController : new MainCtlr(this);
	}

	public MainVw getMainView(MainCtlr ctl) {
		return mainView != null ? mainView : new MainVw(this, ctl);
	}

	public MainModl getMainModel(MainCtlr ctl) {
		return mainModel != null ? mainModel : new MainModl(this, ctl);
	}
	
	public Locale getLocale() {
		return this.locale;
	}

}
