package org.raspinloop.fmi.plugin.preferences.extension;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.raspinloop.config.HardwareConfig;
import org.raspinloop.fmi.plugin.Activator;

/**
 * A wizard page used to edit the attributes of a RIL hardware. 
 * <p>
 * A RIL hardware Configuration page is contributed via the <code>rilHardwareConfigPages</code> extension
 *  point. Following is an example definition of a RIL hardware Configuration page.
 * <pre>
 * &lt;extension point="org.raspinloop.fmi.preferences.rilHardwareConfigPages"&gt;
 *   &lt;Editor 
 *      implementationClassName = "org.raspinloop.pi4j.io.gpio.SimulatedStepperMotorProperties"
 *      class="org.raspinloop.pi4j.io.gpio.SimulatedStepperMotorPropertiesPage"&gt;
 *   &lt;/Editor&gt;
 * &lt;/extension&gt;
 * </pre>
 * The attributes are specified as follows:
 * <ul> 
 * <li><code>class</code> Wizard page implementation. Must be a subclass of
 *  <code>org.raspinloop.fmi.plugin.preferences.HWConfigWizard</code>.</li>
 * </ul>
 * </p>
 * <p>
 * Clients contributing a custom RIL hardware Configuration page via the <code>rilHardwareConfigPages</code> 
 * extension point must subclass this class.
 * </p>
 * @since 3.3
 */
public abstract class AbstractHWConfigPage extends WizardPage {
	
	/**
	 * Name of the original HW being edited, or <code>null</code> if none.
	 */
	private String fOriginalName = null;
	
	/**
	 * Status of VM name (to notify of name already in use)
	 */
	private IStatus fNameStatus = Status.OK_STATUS;
	
	private String[] fExistingNames;
		
	/**
	 * Constructs a new page with the given page name.
	 * 
	 * @param pageName the name of the page
	 */
	protected AbstractHWConfigPage(String pageName) {
		super(pageName);
	}

	/**
     * Creates a new wizard page with the given name, title, and image.
     *
     * @param pageName the name of the page
     * @param title the title for this wizard page,
     *   or <code>null</code> if none
     * @param titleImage the image descriptor for the title of this wizard page,
     *   or <code>null</code> if none
     */
	protected AbstractHWConfigPage(String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}

	/**
	 * Called when the RIL hardware Configuration page wizard is closed by selecting 
	 * the finish button. Implementers typically override this method to 
	 * store the page result (new/changed RIL Hardware Configuration returned in 
	 * getSelection) into its model.
	 * 
	 * @return if the operation was successful. Only when returned
	 * <code>true</code>, the wizard will close.
	 */
	public abstract boolean finish();
	
	/**
	 * Returns the edited or created RIL Hardware Configuration. This method
	 * may return <code>null</code> if no RIL Hardware Configuration exists.
	 * 
	 * @return the edited or created RIL Hardware Configuration.
	 */
	public abstract HardwareConfig getSelection();

	/**
	 * Sets the VM install to be edited. 
	 * 
	 * @param vm the VM install to edit
	 */
	public void setSelection(HardwareConfig hw) {
		fOriginalName = hw.getType();
	}
	
	/**
	 * Updates the name status based on the new name. This method should be called
	 * by the page each time the RIL Hardware name changes.
	 * 
	 * @param newName new name of RIL Hardware
	 */
	protected void nameChanged(String newName) {
		fNameStatus = Status.OK_STATUS;
		if (newName == null || newName.trim().length() == 0) {
			int sev = IStatus.ERROR;
			if (fOriginalName == null || fOriginalName.length() == 0) {
				sev = IStatus.WARNING;
			}
			fNameStatus = new Status(sev, Activator.PLUGIN_ID, "Enter the name for the Simulated Hardware");
		} else {
			if (isDuplicateName(newName)) {
				fNameStatus = new Status(IStatus.ERROR, Activator.PLUGIN_ID, "The simulated Hardware name already exists"); 
			}
		}
		updatePageStatus();
	}
	
	/**
	 * Returns whether the name is already in use by an existing RIL Hardware
	 * 
	 * @param name new name
	 * @return whether the name is already in use
	 */
	private boolean isDuplicateName(String name) {
		if (fExistingNames != null) {
			for (int i = 0; i < fExistingNames.length; i++) {
				if (name.equals(fExistingNames[i])) {
					return true;
				}
			}
		}
		return false;
	}	
	
	/**
	 * Sets the names of existing RIL Hardware , not including the RIL Hardware being edited. This method
	 * is called by the wizard and clients should not call this method.
	 * 
	 * @param names existing RIL Hardware names or an empty array
	 */
	public void setExistingNames(String[] names) {
		fExistingNames = names;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#getNextPage()
	 */
	@Override
	public IWizardPage getNextPage() {
		return null;
	}
	
	/**
	 * Sets this page's message based on the status severity.
	 * 
	 * @param status status with message and severity
	 */
	protected void setStatusMessage(IStatus status) {
		if (status.isOK()) {
			setMessage(status.getMessage());
		} else {
			switch (status.getSeverity()) {
			case IStatus.ERROR:
				setMessage(status.getMessage(), IMessageProvider.ERROR);
				break;
			case IStatus.INFO:
				setMessage(status.getMessage(), IMessageProvider.INFORMATION);
				break;
			case IStatus.WARNING:
				setMessage(status.getMessage(), IMessageProvider.WARNING);
				break;
			default:
				break;
			}
		}
	}	
	
	/**
	 * Returns the current status of the name being used for the RIL Hardware.
	 * 
	 * @return status of current RIL Hardware name
	 */
	protected IStatus getNameStatus() {
		return fNameStatus;
	}
	
	/**
	 * Updates the status message on the page, based on the status of the RIL Hardware and other
	 * status provided by the page.
	 */
	protected void updatePageStatus() {
		IStatus max = Status.OK_STATUS;
		IStatus[] hwStatus = getHWStatus();
		for (int i = 0; i < hwStatus.length; i++) {
			IStatus status = hwStatus[i];
			if (status.getSeverity() > max.getSeverity()) {
				max = status;
			}
		}
		if (fNameStatus.getSeverity() > max.getSeverity()) {
			max = fNameStatus;
		}
		if (max.isOK()) {
			setMessage(null, IMessageProvider.NONE);
		} else {
			setStatusMessage(max);
		}
		setPageComplete(max.isOK() || max.getSeverity() == IStatus.INFO);
	}	
	
	/**
	 * Returns a collection of status messages pertaining to the current edit
	 * status of the RIL Hardware on this page. An empty collection or a collection of
	 * OK status objects indicates all is well.
	 * 
	 * @return collection of status objects for this page
	 */
	protected abstract IStatus[] getHWStatus();
}
