/*******************************************************************************
 * Copyright (C) 2018 RaspInLoop
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.raspinloop.fmi.plugin.preferences;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.statushandlers.StatusManager;
import org.osgi.service.prefs.BackingStoreException;
import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.GsonProperties;
import org.raspinloop.config.HardwareProperties;
import org.raspinloop.fmi.plugin.Activator;

/**
 * 
 * @author Fred
 *
 *         Editor page used to list , add edit and remove RaspInLoop hardware
 *         page
 */
public class RilManageHardwarePage extends PreferencePage implements IWorkbenchPreferencePage {


	public static final String ID = "org.raspinloop.fmi.plugin.launcher.RilManageHardwarePage"; //$NON-NLS-1$

	public RilManageHardwarePage() {
		super();
	}

	private RilHarwareListBlock rilHwBlock;
	private IEclipsePreferences preferences;

	@Override
	public void init(IWorkbench workbench) {
		setDescription("Add, edit or remove simulated hardware platform");
		preferences = ConfigurationScope.INSTANCE.getNode("org.raspinloop.fmi.preferences.configuredhardware");
	}

	@Override
	protected Control createContents(Composite parent) {
		initializeDialogUnits(parent);

		noDefaultAndApplyButton();

		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		parent.setLayout(layout);

		// SWTFactory.createWrapLabel(parent, JREMessages.JREsPreferencePage_2,
		// 1, 300);
		SWTFactory.createVerticalSpacer(parent, 1);
		List<Class<? extends HardwareProperties>> supportedTypes = new LinkedList<>();
		supportedTypes.add(BoardHardware.class);
		rilHwBlock = new RilHarwareListBlock(null, supportedTypes);
		rilHwBlock.setEditAfterAdd(true);
		rilHwBlock.createControl(parent);
		
		Control control = rilHwBlock.getControl();
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 1;
		control.setLayoutData(data);

		rilHwBlock.addAddHWListener(new AddNewHardwareListener());
		rilHwBlock.addDeleteHWListener(new RemoveHardwareListener());
		rilHwBlock.addEditHWListener(new UpdateHardwareListener());		
		rilHwBlock.addExportHWListener(new ExportHardwareListener());
		rilHwBlock.restoreColumnSettings(Activator.getDefault().getDialogSettings(), PreferenceConstants.RIL_PREFERENCE_PAGE);

		PluggedHardwareEnumerator enumerator = new PluggedHardwareEnumerator();
	
		List<HardwareProperties> hws = new ArrayList<HardwareProperties>();
		String listStr = preferences.get("HwList", "");
		String[] hwNames = listStr.split(":");
		for (String hwName : hwNames) {
			String bytes = preferences.get(hwName, "");
			if (bytes.trim().length() != 0) {
				try {
				GsonProperties conf = new GsonProperties(enumerator);
				HardwareProperties hw = conf.read(bytes);
				if (hw != null)
					hws.add(hw);
				} catch (Exception e)
				{
				}
			}
		}

		rilHwBlock.setHwList(hws);

		applyDialogFont(parent);
		return parent;
	}
	

	protected String getHwNameList(RilHarwareListBlock fRilHwBlock) {
		ArrayList<String> names = new ArrayList<>(fRilHwBlock.getHWs().length);
		for (HardwareProperties hw : fRilHwBlock.getHWs()) {
			names.add(hw.getComponentName());
		}
		return join(names, ":");
	}

	private final class RemoveHardwareListener implements IHWListener {
		@Override
		public void addOrRemoveHW(HardwareProperties hw) {
			try {

				preferences.remove(hw.getComponentName());
				preferences.put("HwList", getHwNameList(rilHwBlock));
				preferences.flush();
			} catch (BackingStoreException e) {
				IStatus status = new Status(Status.ERROR, Activator.PLUGIN_ID, e.getMessage());
				// Let the StatusManager handle the Status and provide a hint
				StatusManager.getManager().handle(status, StatusManager.LOG | StatusManager.SHOW);
			}
		}
	}


	private final class UpdateHardwareListener implements IHWListener {

		@Override
		public void addOrRemoveHW(HardwareProperties hw) {
			try {
				GsonProperties conf = new GsonProperties(new PluggedHardwareEnumerator());
				if (hw instanceof BoardHardware) {
					preferences.put(hw.getComponentName(), conf.write((BoardHardware) hw));
					preferences.put("HwList", getHwNameList(rilHwBlock));
					preferences.flush();
				}
			} catch (BackingStoreException e) {
				IStatus status = new Status(Status.ERROR, Activator.PLUGIN_ID, e.getMessage());
				// Let the StatusManager handle the Status and provide a hint
				StatusManager.getManager().handle(status, StatusManager.LOG | StatusManager.SHOW);
			}
		}

	}
	
	private final class AddNewHardwareListener implements IHWListener {
		@Override
		public void addOrRemoveHW(HardwareProperties hw) {
			try {
				GsonProperties conf = new GsonProperties(new PluggedHardwareEnumerator());
				if (hw instanceof BoardHardware) {
					preferences.put(hw.getComponentName(), conf.write((BoardHardware) hw));
					preferences.put("HwList", getHwNameList(rilHwBlock));
					preferences.flush();
				}
			} catch (BackingStoreException e) {
				IStatus status = new Status(Status.ERROR, Activator.PLUGIN_ID, e.getMessage());
				// Let the StatusManager handle the Status and provide a hint
				StatusManager.getManager().handle(status, StatusManager.LOG | StatusManager.SHOW);
			}
		}
	}
		
	private final class ExportHardwareListener implements IHWListener {

		@Override
		public void addOrRemoveHW(HardwareProperties hw) {
			try {
				FileDialog dialog = new FileDialog(RilManageHardwarePage.this.getShell(), SWT.SAVE);
				// wild
				dialog.setFilterNames(new String[] { "hwdesc Files", "All Files (*.*)" });
				dialog.setFilterExtensions(new String[] { "*.json", "*.*" }); // Windows
				// cards
				dialog.setFilterPath(System.getProperty("user.home")); // Windows path
				
				GsonProperties conf = new GsonProperties(new PluggedHardwareEnumerator());
				String serializedHw = conf.write((BoardHardware) hw);
				
				dialog.setFileName(hw.getComponentName() + ".json");
				String fileName = dialog.open();
				if (fileName != null && !fileName.isEmpty()) {
					File file = new File(fileName);
					if (file.exists()) {
						if (!MessageDialog.openQuestion(RilManageHardwarePage.this.getShell(), "Overwrite", "File already exist!\n Do you want to overwrite it ?")) {
							return;
						}
						file.delete();
					}
					Files.write(file.toPath(), serializedHw.getBytes(),StandardOpenOption.CREATE_NEW);
				}					
				
			} catch (Exception e) {
				IStatus status = new Status(Status.ERROR, Activator.PLUGIN_ID, e.getMessage());
				// Let the StatusManager handle the Status and provide a hint
				StatusManager.getManager().handle(status, StatusManager.LOG | StatusManager.SHOW);
			}
		}

	}

	public static String join(Collection<String> list, String delim) {

		StringBuilder sb = new StringBuilder();

		String loopDelim = "";

		for (String s : list) {

			sb.append(loopDelim);
			sb.append(s);

			loopDelim = delim;
		}

		return sb.toString();
	}
}
