package org.raspinloop.fmi.plugin.preferences;

import org.raspinloop.config.HardwareConfig;
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
	public EditHwWizard(HardwareConfig hw, HardwareConfig[] allHWs) {
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
	protected HardwareConfig getResult() {
		return fEditPage.getSelection();
	}

}
