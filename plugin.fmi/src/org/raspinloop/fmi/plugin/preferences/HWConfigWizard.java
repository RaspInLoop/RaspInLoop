package org.raspinloop.fmi.plugin.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.wizard.Wizard;
import org.raspinloop.config.HardwareConfig;
import org.raspinloop.fmi.plugin.Activator;
import org.raspinloop.fmi.plugin.preferences.extension.AbstractHWConfigPage;

/**
 * @since 3.3
 * 
 */
public abstract class HWConfigWizard extends Wizard {
	
	private HardwareConfig fEditHW;
	private String[] fExistingNames;
	
	/**
	 * Constructs a new wizard to add/edit an Hardware.
	 * 
	 * @param editHW the Hardware being edited, or <code>null</code> if none
	 * @param currentHWs current Hardware used to validate name changes
	 */
	public HWConfigWizard(HardwareConfig editHW, HardwareConfig[] currentHWs) {
		fEditHW = editHW;
		List<String> names = new ArrayList<String>(currentHWs.length);
		for (int i = 0; i < currentHWs.length; i++) {
			HardwareConfig existing = currentHWs[i];
			if (!existing.equals(editHW)) {
				names.add(existing.getComponentName());
			}
		}
		fExistingNames = names.toArray(new String[names.size()]);
	}
	
	/**
	 * Returns the HW to edit, or <code>null</code> if creating a HW
	 * 
	 * @return hw to edit or <code>null</code>
	 */
	protected HardwareConfig getHWConfig() {
		return fEditHW;
	}
	
	/**
	 * Returns the resulting VM after edit or creation or <code>null</code> if none.
	 * 
	 * @return resulting VM
	 */
	protected abstract HardwareConfig getResult();

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		return getResult() != null;
	}
	
	/**
	 * Returns a page to use for editing an Hardware type
	 * 
	 * @param hardwareDefinitionDelegate.getType()
	 * @return
	 */
	public AbstractHWConfigPage getPage(String implementationClassName) {
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(PreferenceConstants.HARDWARE_EXTENSION_POINT_ID);
		IConfigurationElement[] infos= extensionPoint.getConfigurationElements();
		for (int i = 0; i < infos.length; i++) {
			IConfigurationElement element = infos[i];
			String id = element.getAttribute("simulatorClass"); //$NON-NLS-1$
			if (implementationClassName.equals(id)) {
				try {
					AbstractHWConfigPage page = (AbstractHWConfigPage) element.createExecutableExtension("editorClass"); //$NON-NLS-1$
					page.setExistingNames(fExistingNames);
					return page;
				} catch (CoreException e) {
					Activator.getDefault().logError("Cannot get editor page for "+implementationClassName, e);				}
			}
		}
		return null;
	}

	
}
