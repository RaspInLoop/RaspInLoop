package org.raspinloop.fmi.plugin.preferences;

import org.raspinloop.config.HardwareProperties;
import org.raspinloop.fmi.plugin.preferences.extension.AbstractHWConfigPage;

public class EditHwWizard extends HWConfigWizard {

	private AbstractHWConfigPage fEditPage;

	/**
	 * Constructs a wizard to edit the given Hardware.
	 * 
	 * @param hw
	 *            vm to edit
	 * @param allHWs
	 *            all Hardware being edited
	 */
	public EditHwWizard(HardwareProperties hw, HardwareProperties[] allHWs) {
		super(hw, allHWs);
		setWindowTitle("Edit RIL Hardware");
	}

	@Override
	public void addPages() {
		fEditPage = getPage(getHWConfig().getImplementationClassName());
		if (fEditPage != null) {
			fEditPage.setSelection((getHWConfig()));
			addPage(fEditPage);
		}
	}

	@Override
	public boolean performFinish() {
		if (fEditPage.finish()) {
			return super.performFinish();
		}
		return false;
	}

	@Override
	protected HardwareProperties getResult() {
		if (fEditPage != null)
			return fEditPage.getSelection();
		else return null;
	}

}
