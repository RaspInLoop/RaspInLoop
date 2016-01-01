package org.raspinloop.fmi.plugin.preferences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.statushandlers.StatusManager;
import org.osgi.service.prefs.BackingStoreException;
import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.GsonConfig;
import org.raspinloop.config.HardwareConfig;
import org.raspinloop.fmi.plugin.Activator;


/**
 * 
 * @author Motte
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

		PluggedHardwareEnumerator enumerator = new PluggedHardwareEnumerator();
		ArrayList<HardwareConfig> list = enumerator.buildListImplementing(BoardHardware.class);
		rilHwBlock = new RilHarwareListBlock(null, list );
		rilHwBlock.setEditAfterAdd(true);
		rilHwBlock.createControl(parent);
		
		Control control = rilHwBlock.getControl();
		GridData data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 1;
		control.setLayoutData(data);

		rilHwBlock.addAddHWListener(new AddNewHardwareListener());
		rilHwBlock.addDeleteHWListener(new RemoveHardwareListener());
		rilHwBlock.addEditHWListener(new UpdateHardwareListener());
		rilHwBlock.restoreColumnSettings(Activator.getDefault().getDialogSettings(), PreferenceConstants.RIL_PREFERENCE_PAGE);

		List<HardwareConfig> hws = new ArrayList<HardwareConfig>();
		String listStr = preferences.get("HwList", "");
		String[] hwNames = listStr.split(":");
		for (String hwName : hwNames) {
			String bytes = preferences.get(hwName, "");
			if (bytes.trim().length() != 0) {
				try {
				GsonConfig conf = new GsonConfig(enumerator);
				HardwareConfig hw = conf.read(bytes);
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
		for (HardwareConfig hw : fRilHwBlock.getHWs()) {
			names.add(hw.getName());
		}
		return join(names, ":");
	}

	private final class RemoveHardwareListener implements IHWListener {
		@Override
		public void addOrRemoveHW(HardwareConfig hw) {
			try {

				preferences.remove(hw.getName());
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
		public void addOrRemoveHW(HardwareConfig hw) {
			try {
				GsonConfig conf = new GsonConfig(new PluggedHardwareEnumerator());
				if (hw instanceof BoardHardware) {
					preferences.put(hw.getName(), conf.write((BoardHardware) hw));
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
		public void addOrRemoveHW(HardwareConfig hw) {
			try {
				GsonConfig conf = new GsonConfig(new PluggedHardwareEnumerator());
				if (hw instanceof BoardHardware) {
					preferences.put(hw.getName(), conf.write((BoardHardware) hw));
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
