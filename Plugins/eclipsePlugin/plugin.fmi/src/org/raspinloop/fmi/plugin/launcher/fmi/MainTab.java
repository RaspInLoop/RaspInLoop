package org.raspinloop.fmi.plugin.launcher.fmi;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.statushandlers.StatusManager;
import org.raspinloop.config.HardwareBuilderFactory;
import org.raspinloop.config.HardwareProperties;
import org.raspinloop.fmi.FMU;
import org.raspinloop.fmi.HwEmulation;
import org.raspinloop.fmi.plugin.Activator;
import org.raspinloop.fmi.plugin.configuration.SimulationType;
import org.raspinloop.fmi.plugin.launcher.PluggedClassBuilderFactory;
import org.raspinloop.fmi.plugin.launcher.RilMainTab;

public class MainTab extends RilMainTab {

	protected Button fgetFMUButton;

	@Override
	protected void displaySimulationType() {		
		fgetFMUButton.setVisible(true);
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy config) {
		super.performApply(config);
		
		config.setAttribute(ATTR_SIMULATION_TYPE, SimulationType.FMU.toString());
	}
	
	@Override
	protected void createSimulationTypeEditor(Composite parent) {
		{
			Group group = SWTFactory.createGroup(parent, "Remote Simulation Parammeters:",3, 1, GridData.FILL_HORIZONTAL);

			fgetFMUButton = createPushButton(group, "Get FMU file... ", null);
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan = 3;
			fgetFMUButton.setLayoutData(gd);
			fgetFMUButton.addSelectionListener(new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {
				}

				public void widgetSelected(SelectionEvent e) {
					getFMUButtonSelected();
				}
			});
			
		}
	}
	
	private void getFMUButtonSelected() {
		try {
			HardwareProperties hwProperties = (HardwareProperties) ((IStructuredSelection) fHardwareCombo.getSelection()).getFirstElement();
			if (hwProperties != null) {

				FileDialog dialog = new FileDialog(compositeParent.getShell(), SWT.SAVE);
				// wild
				dialog.setFilterNames(new String[] { "FMU Files", "All Files (*.*)" });
				dialog.setFilterExtensions(new String[] { "*.fmu", "*.*" }); // Windows
				// cards
				dialog.setFilterPath(System.getProperty("user.home")); // Windows path
				dialog.setFileName(hwProperties.getComponentName() + ".fmu");
				String fileName = dialog.open();
				if (fileName != null && !fileName.isEmpty()) {
					File file = new File(fileName);
					if (file.exists()) {
						if (!MessageDialog.openQuestion(compositeParent.getShell(), "Overwrite", "File already exist!\n Do you want to overwrite it ?")) {
							return;
						}
						file.delete();
					}
					FMU.Locator eclipseLocator = new FMU.Locator() {
						public URL resolve(URL url) {
							try {
								return FileLocator.resolve(url);
							} catch (IOException e) {
								System.err.print(e);
							}
							return null;
						}
					};
					FMU.generate(file, hwProperties);
				}

			}

		} catch (Exception e) {
			IStatus status = new Status(Status.ERROR, Activator.PLUGIN_ID, e.getMessage());
			StatusManager.getManager().handle(status, StatusManager.LOG | StatusManager.SHOW);
		}
	}
}
