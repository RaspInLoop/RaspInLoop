package org.raspinloop.pi4j.io.i2c;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.widgets.Composite;
import org.raspinloop.config.HardwareProperties;
import org.raspinloop.fmi.plugin.preferences.extension.AbstractHWConfigPage;

public class TSL256xPropertiesPage extends AbstractHWConfigPage {

	protected TSL256xPropertiesPage(String pageName) {
		super(pageName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean finish() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HardwareProperties getSelection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IStatus[] getHWStatus() {
		// TODO Auto-generated method stub
		return null;
	}

}
