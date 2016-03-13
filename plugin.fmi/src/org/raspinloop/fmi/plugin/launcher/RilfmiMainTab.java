package org.raspinloop.fmi.plugin.launcher;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.debug.ui.IJavaDebugUIConstants;
import org.eclipse.jdt.internal.debug.ui.IJavaDebugHelpContextIds;
import org.eclipse.jdt.internal.debug.ui.launcher.DebugTypeSelectionDialog;
import org.eclipse.jdt.internal.debug.ui.launcher.LauncherMessages;
import org.eclipse.jdt.internal.debug.ui.launcher.MainMethodSearchEngine;
import org.eclipse.jdt.internal.debug.ui.launcher.SharedJavaMainTab;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.statushandlers.StatusManager;
import org.raspinloop.config.HardwareConfig;
import org.raspinloop.fmi.hwemulation.GpioProviderHwEmulation;
import org.raspinloop.fmi.hwemulation.HardwareBuilderFactory;
import org.raspinloop.fmi.hwemulation.HwEmulation;
import org.raspinloop.fmi.internal.fmu.FMU;
import org.raspinloop.fmi.plugin.Activator;
import org.raspinloop.fmi.plugin.configuration.HardwareContentProvider;
import org.raspinloop.fmi.plugin.configuration.HardwareLabelProvider;
import org.raspinloop.fmi.plugin.configuration.SimulationLabelProvider;
import org.raspinloop.fmi.plugin.configuration.SimulationType;
import org.raspinloop.fmi.plugin.configuration.SimulationTypeContentProvider;
import org.raspinloop.fmi.plugin.configuration.TimeUnitContentProvider;
import org.raspinloop.fmi.plugin.configuration.TimeUnitLabelProvider;
import org.raspinloop.fmi.plugin.preferences.RilManageHardwarePage;

@SuppressWarnings("restriction")
public class RilfmiMainTab extends SharedJavaMainTab {

	

	private static final TimeUnit[] AVAILABLE_TIME_UNIT = new TimeUnit[]{TimeUnit.MICROSECONDS, TimeUnit.MILLISECONDS, TimeUnit.SECONDS, TimeUnit.MINUTES, TimeUnit.HOURS, TimeUnit.DAYS};
	/**
	 * Boolean launch configuration attribute indicating that external jars (on
	 * the runtime classpath) should be searched when looking for a main type.
	 * Default value is <code>false</code>.
	 * 
	 * @since 2.1
	 */
	public static final String ATTR_INCLUDE_EXTERNAL_JARS = IJavaDebugUIConstants.PLUGIN_ID + ".INCLUDE_EXTERNAL_JARS"; //$NON-NLS-1$
	/**
	 * Boolean launch configuration attribute indicating whether types
	 * inheriting a main method should be considered when searching for a main
	 * type. Default value is <code>false</code>.
	 * 
	 * @since 3.0
	 */
	public static final String ATTR_CONSIDER_INHERITED_MAIN = IJavaDebugUIConstants.PLUGIN_ID + ".CONSIDER_INHERITED_MAIN"; //$NON-NLS-1$	

	public static final String ATTR_HARDWARE_CONFIG = IJavaDebugUIConstants.PLUGIN_ID + ".HARDWARE_CONFIG"; //$NON-NLS-1$
	public static final String ATTR_SIMULATION_TYPE= IJavaDebugUIConstants.PLUGIN_ID + ".SIMULATION_TYPE"; //$NON-NLS-1$
	public static final String ATTR_STANDALONE_TIME_INCREMENT= IJavaDebugUIConstants.PLUGIN_ID + ".STANDALONE_TIME_INCREMENT"; //$NON-NLS-1$
	public static final String ATTR_STANDALONE_TIME_INCREMENT_UNIT= IJavaDebugUIConstants.PLUGIN_ID + ".STANDALONE_TIME_INCREMENT_UNIT"; //$NON-NLS-1$
	public static final String ATTR_STANDALONE_TIME_RATIO= IJavaDebugUIConstants.PLUGIN_ID + ".STANDALONE_TIME_RATIO"; //$NON-NLS-1$
	public static final String ATTR_STANDALONE_END_TIME= IJavaDebugUIConstants.PLUGIN_ID + ".ATTR_STANDALONE_END_TIME"; //$NON-NLS-1$
	public static final String ATTR_STANDALONE_END_TIME_UNIT= IJavaDebugUIConstants.PLUGIN_ID + ".ATTR_STANDALONE_END_TIME_UNIT"; //$NON-NLS-1$

	// UI widgets
	private Button fSearchExternalJarsCheckButton;
	private Button fConsiderInheritedMainButton;

	private Button fgetFMUButton;
	private Button fManageHardwareButton;
	private ComboViewer fHardwareCombo;
	private Collection<HardwareConfig> hardwares;
	private Composite compositeParent;
	private ComboViewer fSimulationCombo;
	private Spinner fStandAloneTimeIncrement;
	private Spinner fStandAloneTimeRatio;
	private Spinner fStandAloneEndTime;
	private Label lStandAloneTimeIncrement;
	private Label lStandAloneTimeRatio;
	private Label lStandAloneEndTime;
	private ComboViewer fStandAloneTimeIncrementCombo;
	private ComboViewer fStandAloneEndTimeCombo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#createControl(org.eclipse
	 * .swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		compositeParent = parent;
		Composite comp = SWTFactory.createComposite(parent, parent.getFont(), 1, 1, GridData.FILL_BOTH);
		((GridLayout) comp.getLayout()).verticalSpacing = 0;
		createProjectEditor(comp);
		createVerticalSpacer(comp, 1);
		createMainTypeEditor(comp, LauncherMessages.JavaMainTab_Main_cla_ss__4);
		setControl(comp);
		createHardwareTypeEditor(comp, "Hardware Definition:");
		createSimulationTypeEditor(comp, "Simulation Mode:");

		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), IJavaDebugHelpContextIds.LAUNCH_CONFIGURATION_DIALOG_MAIN_TAB);
	}

	/**
	 * Creates the widgets for specifying The hardware to use
	 * 
	 * @param parent
	 *            the parent composite
	 */
	protected void createHardwareTypeEditor(Composite parent, String text) {
		Group group = SWTFactory.createGroup(parent, text, 2, 1, GridData.FILL_HORIZONTAL);
		fHardwareCombo = createComboViewer(group, SWT.DROP_DOWN | SWT.READ_ONLY, 1);
		fHardwareCombo.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				updateLaunchConfigurationDialog();
			}
		});
		fHardwareCombo.setContentProvider(new HardwareContentProvider());
		fHardwareCombo.setLabelProvider(new HardwareLabelProvider());

		fManageHardwareButton = createPushButton(group, "Configure Hardware...", null);
		fManageHardwareButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				showPrefPage(RilManageHardwarePage.ID);
			}
		});
	
	}
	
	/**
	 * Creates the widgets for specifying The Kind of simulation
	 * 
	 * @param parent
	 *            the parent composite
	 */
	protected void createSimulationTypeEditor(Composite parent, String text) {
		Group group = SWTFactory.createGroup(parent, text,3, 1, GridData.FILL_HORIZONTAL);
		fSimulationCombo = createComboViewer(group, SWT.DROP_DOWN | SWT.READ_ONLY, 3);
		fSimulationCombo.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				displaySimulationType();
				updateLaunchConfigurationDialog();
			}
		});
		fSimulationCombo.setContentProvider(new SimulationTypeContentProvider());
		fSimulationCombo.setLabelProvider(new SimulationLabelProvider());

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
		
		lStandAloneTimeIncrement = addLabel(group, "Time Increment: ");
		fStandAloneTimeIncrement = new Spinner(group, SWT.NONE);
		fStandAloneTimeIncrement.setMinimum(0);
		fStandAloneTimeIncrement.setMaximum(1000);
		fStandAloneTimeIncrement.setIncrement(1);
		fStandAloneTimeIncrement.setSelection(1);
		fStandAloneTimeIncrement.addModifyListener(new ModifyListener() {			
			@Override
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
		
		fStandAloneTimeIncrementCombo = createComboViewer(group, SWT.DROP_DOWN | SWT.READ_ONLY, 1);
		fStandAloneTimeIncrementCombo.setContentProvider(new TimeUnitContentProvider());
		fStandAloneTimeIncrementCombo.setLabelProvider(new TimeUnitLabelProvider());
		fStandAloneTimeIncrementCombo.setInput(AVAILABLE_TIME_UNIT);
		fStandAloneTimeIncrementCombo.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				updateLaunchConfigurationDialog();
			}
		});
		
		lStandAloneEndTime = addLabel(group, "End Time:");
		fStandAloneEndTime = new Spinner(group, SWT.NONE);
		fStandAloneEndTime.setMinimum(0);
		fStandAloneEndTime.setMaximum(100000);
		fStandAloneEndTime.setIncrement(1);
		fStandAloneEndTime.setSelection(1);	
		fStandAloneEndTime.addModifyListener(new ModifyListener() {			
			@Override
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
		
		fStandAloneEndTimeCombo = createComboViewer(group, SWT.DROP_DOWN | SWT.READ_ONLY, 1);
		fStandAloneEndTimeCombo.setContentProvider(new TimeUnitContentProvider());
		fStandAloneEndTimeCombo.setLabelProvider(new TimeUnitLabelProvider());
		fStandAloneEndTimeCombo.setInput(AVAILABLE_TIME_UNIT);
		fStandAloneEndTimeCombo.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				updateLaunchConfigurationDialog();
			}
		});
		
		lStandAloneTimeRatio = addLabel(group, "Time Ratio (%):");
		fStandAloneTimeRatio = new Spinner(group, SWT.NONE);
		fStandAloneTimeRatio.setDigits(3);
		fStandAloneTimeRatio.setMinimum(0);
		fStandAloneTimeRatio.setMaximum(100000);
		fStandAloneTimeRatio.setIncrement(1000);
		fStandAloneTimeRatio.setSelection(1000);
		fStandAloneTimeRatio.addModifyListener(new ModifyListener() {			
			@Override
			public void modifyText(ModifyEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
	}

	
	private Label addLabel(Composite composite, String text) {
		Label l = new Label(composite, SWT.NONE);
		l.setFont(composite.getFont());
		l.setText(text);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = false;
		gd.verticalAlignment = SWT.BEGINNING;
		l.setLayoutData(gd);
		return l;
	}
	
	private ComboViewer createComboViewer(Group parent, int style, int hspan) {
		ComboViewer c = new ComboViewer(parent, style);
		c.getCombo().setFont(parent.getFont());
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = hspan;
		c.getCombo().setLayoutData(gd);

		// Some platforms open up combos in bad sizes without this, see bug
		// 245569
		c.getCombo().setVisibleItemCount(30);
		c.getCombo().select(0);
		return c;
	}

	protected void showPrefPage(String pageId) {
		if (PreferencesUtil.createPreferenceDialogOn(getShell(), pageId, new String[] { pageId }, null).open() == Window.OK) {
			hardwares = HardwareConfiguration.buildList();
			fHardwareCombo.setInput(hardwares.toArray(new HardwareConfig[hardwares.size()]));
		}
		fSimulationCombo.setInput(SimulationType.values());
	}

	protected void getFMUButtonSelected() {
		try {
			HardwareConfig hwProperties = (HardwareConfig) ((IStructuredSelection) fHardwareCombo.getSelection()).getFirstElement();
			if (hwProperties != null) {
				HardwareBuilderFactory hbf = new PluggedClassBuilderFactory();
				HwEmulation emulationImplementation = hbf.createBuilder(hwProperties).build();
				if (emulationImplementation instanceof GpioProviderHwEmulation) {
					FileDialog dialog = new FileDialog(compositeParent.getShell(), SWT.SAVE);
					dialog.setFilterNames(new String[] { "FMU Files", "All Files (*.*)" });
					dialog.setFilterExtensions(new String[] { "*.fmu", "*.*" }); // Windows
					// wild
					// cards
					dialog.setFilterPath("c:\\"); // Windows path
					dialog.setFileName(hwProperties.getName() + ".fmu");
					String fileName = dialog.open();
					if (fileName != null && !fileName.isEmpty()) {
						File file = new File(fileName);
						if (file.exists()) {
							if (!MessageDialog.openQuestion(compositeParent.getShell(), "Overwrite", "File already exist!\n Do you want to overwrite it ?")) {
								return;
							}
							file.delete();
						}
						FMU.generate(file, (GpioProviderHwEmulation) emulationImplementation);
					}

				}
			}
		} catch (Exception e) {
			IStatus status = new Status(Status.ERROR, Activator.PLUGIN_ID, e.getMessage());
			StatusManager.getManager().handle(status, StatusManager.LOG | StatusManager.SHOW);
		}
	}

	/**
	 * @see org.eclipse.jdt.internal.debug.ui.launcher.SharedJavaMainTab#createMainTypeExtensions(org.eclipse.swt.widgets.Composite)
	 */

	@Override
	protected void createMainTypeExtensions(Composite parent) {
		fSearchExternalJarsCheckButton = SWTFactory.createCheckButton(parent, LauncherMessages.JavaMainTab_E_xt__jars_6, null, false, 2);
		fSearchExternalJarsCheckButton.addSelectionListener(getDefaultListener());

		fConsiderInheritedMainButton = SWTFactory.createCheckButton(parent, LauncherMessages.JavaMainTab_22, null, false, 2);
		fConsiderInheritedMainButton.addSelectionListener(getDefaultListener());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTab#getName()
	 */
	public String getName() {
		return LauncherMessages.JavaMainTab__Main_19;
	}

	/**
	 * @see org.eclipse.debug.ui.AbstractLaunchConfigurationTab#getId()
	 * 
	 * @since 3.3
	 */
	@Override
	public String getId() {
		return "org.eclipse.jdt.debug.ui.javaMainTab"; //$NON-NLS-1$
	}

	/**
	 * Show a dialog that lists all main types
	 */
	@Override
	protected void handleSearchButtonSelected() {
		IJavaProject project = getJavaProject();
		IJavaElement[] elements = null;
		if ((project == null) || !project.exists()) {
			IJavaModel model = JavaCore.create(ResourcesPlugin.getWorkspace().getRoot());
			if (model != null) {
				try {
					elements = model.getJavaProjects();
				} catch (JavaModelException e) {
					Activator.getDefault().log("", e);
					;
				}
			}
		} else {
			elements = new IJavaElement[] { project };
		}
		if (elements == null) {
			elements = new IJavaElement[] {};
		}
		int constraints = IJavaSearchScope.SOURCES;
		constraints |= IJavaSearchScope.APPLICATION_LIBRARIES;
		if (fSearchExternalJarsCheckButton.getSelection()) {
			constraints |= IJavaSearchScope.SYSTEM_LIBRARIES;
		}
		IJavaSearchScope searchScope = SearchEngine.createJavaSearchScope(elements, constraints);
		MainMethodSearchEngine engine = new MainMethodSearchEngine();
		IType[] types = null;
		try {
			types = engine.searchMainMethods(getLaunchConfigurationDialog(), searchScope, fConsiderInheritedMainButton.getSelection());
		} catch (InvocationTargetException e) {
			setErrorMessage(e.getMessage());
			return;
		} catch (InterruptedException e) {
			setErrorMessage(e.getMessage());
			return;
		}
		DebugTypeSelectionDialog mmsd = new DebugTypeSelectionDialog(getShell(), types, LauncherMessages.JavaMainTab_Choose_Main_Type_11);
		if (mmsd.open() == Window.CANCEL) {
			return;
		}
		Object[] results = mmsd.getResult();
		IType type = (IType) results[0];
		if (type != null) {
			fMainText.setText(type.getFullyQualifiedName());
			fProjText.setText(type.getJavaProject().getElementName());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jdt.internal.debug.ui.launcher.AbstractJavaMainTab#initializeFrom
	 * (org.eclipse.debug.core.ILaunchConfiguration)
	 */
	@Override
	public void initializeFrom(ILaunchConfiguration config) {
		super.initializeFrom(config);
		updateMainTypeFromConfig(config);
		updateInheritedMainsFromConfig(config);
		updateExternalJars(config);
		updateHardwareDefinition(config);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.AbstractLaunchConfigurationTab#isValid(org.eclipse
	 * .debug.core.ILaunchConfiguration)
	 */
	@Override
	public boolean isValid(ILaunchConfiguration config) {
		setErrorMessage(null);
		setMessage(null);
		String name = fProjText.getText().trim();
		if (name.length() > 0) {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IStatus status = workspace.validateName(name, IResource.PROJECT);
			if (status.isOK()) {
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);
				if (!project.exists()) {
					setErrorMessage(NLS.bind(LauncherMessages.JavaMainTab_20, new String[] { name }));
					return false;
				}
				if (!project.isOpen()) {
					setErrorMessage(NLS.bind(LauncherMessages.JavaMainTab_21, new String[] { name }));
					return false;
				}
			} else {
				setErrorMessage(NLS.bind(LauncherMessages.JavaMainTab_19, new String[] { status.getMessage() }));
				return false;
			}
		}
		name = fMainText.getText().trim();
		if (name.length() == 0) {
			setErrorMessage(LauncherMessages.JavaMainTab_Main_type_not_specified_16);
			return false;
		}
		HardwareConfig hardware = (HardwareConfig) ((IStructuredSelection) fHardwareCombo.getSelection()).getFirstElement();
		if (hardware == null) {
			setErrorMessage("You have to select an Hardware Definition");
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#performApply(org.eclipse
	 * .debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void performApply(ILaunchConfigurationWorkingCopy config) {
		config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, fProjText.getText().trim());
		config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, fMainText.getText().trim());
		HardwareConfig hardware = (HardwareConfig) ((IStructuredSelection) fHardwareCombo.getSelection()).getFirstElement();
		if (hardware != null) {
			config.setAttribute(ATTR_HARDWARE_CONFIG, hardware.getName());
		}
		mapResources(config);
		
		// attribute added in 2.1, so null must be used instead of false for
		// backwards compatibility
		config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_STOP_IN_MAIN, (String) null);
		
		config.setAttribute(ATTR_SIMULATION_TYPE, ((SimulationType)((IStructuredSelection)fSimulationCombo.getSelection()).getFirstElement()).toString());
		config.setAttribute(ATTR_STANDALONE_END_TIME,new Integer(fStandAloneEndTime.getSelection()).toString());
		config.setAttribute(ATTR_STANDALONE_TIME_INCREMENT,new Integer(fStandAloneTimeIncrement.getSelection()).toString());
		ISelection endTimeUnitSelection = fStandAloneEndTimeCombo.getSelection();
		if (endTimeUnitSelection instanceof IStructuredSelection){
			Object endTimeUnitSelectedElement = ((IStructuredSelection)endTimeUnitSelection).getFirstElement();
			if (endTimeUnitSelectedElement instanceof TimeUnit)
		    config.setAttribute(ATTR_STANDALONE_END_TIME_UNIT,((TimeUnit)endTimeUnitSelectedElement).toString());
		}
		
		ISelection timeIncrementUnitSelection = fStandAloneTimeIncrementCombo.getSelection();
		if (timeIncrementUnitSelection instanceof IStructuredSelection){
			Object  timeIncrementUnitSelectedElement = ((IStructuredSelection)timeIncrementUnitSelection).getFirstElement();
			if (timeIncrementUnitSelectedElement instanceof TimeUnit)
		    config.setAttribute(ATTR_STANDALONE_TIME_INCREMENT_UNIT,((TimeUnit)timeIncrementUnitSelectedElement).toString());
		}
				
		config.setAttribute(ATTR_STANDALONE_TIME_RATIO,new Double(fStandAloneTimeRatio.getSelection()/1000.0).toString());

		// attribute added in 2.1, so null must be used instead of false for
		// backwards compatibility
		if (fSearchExternalJarsCheckButton.getSelection()) {
			config.setAttribute(ATTR_INCLUDE_EXTERNAL_JARS, true);
		} else {
			config.setAttribute(ATTR_INCLUDE_EXTERNAL_JARS, (String) null);
		}

		// attribute added in 3.0, so null must be used instead of false for
		// backwards compatibility
		if (fConsiderInheritedMainButton.getSelection()) {
			config.setAttribute(ATTR_CONSIDER_INHERITED_MAIN, true);
		} else {
			config.setAttribute(ATTR_CONSIDER_INHERITED_MAIN, (String) null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.debug.ui.ILaunchConfigurationTab#setDefaults(org.eclipse.
	 * debug.core.ILaunchConfigurationWorkingCopy)
	 */
	public void setDefaults(ILaunchConfigurationWorkingCopy config) {
		IJavaElement javaElement = getContext();
		if (javaElement != null) {
			initializeJavaProject(javaElement, config);
		} else {
			config.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, EMPTY_STRING);
		}
		initializeMainTypeAndName(javaElement, config);
	}

	/**
	 * updates the external jars attribute from the specified launch config
	 * 
	 * @param config
	 *            the config to load from
	 */
	private void updateExternalJars(ILaunchConfiguration config) {
		boolean search = false;
		try {
			search = config.getAttribute(ATTR_INCLUDE_EXTERNAL_JARS, false);
		} catch (CoreException e) {
			Activator.getDefault().log("Cannot update External Jar", e);
			;
		}
		fSearchExternalJarsCheckButton.setSelection(search);
	}

	/**
	 * update the inherited mains attribute from the specified launch config
	 * 
	 * @param config
	 *            the config to load from
	 */
	private void updateInheritedMainsFromConfig(ILaunchConfiguration config) {
		boolean inherit = false;
		try {
			inherit = config.getAttribute(ATTR_CONSIDER_INHERITED_MAIN, false);
		} catch (CoreException e) {
			Activator.getDefault().log("Cannot update Inherited", e);
			;
		}
		fConsiderInheritedMainButton.setSelection(inherit);
	}

	private void updateHardwareDefinition(ILaunchConfiguration config) {
		hardwares = HardwareConfiguration.buildList();
		fHardwareCombo.setInput(hardwares.toArray(new HardwareConfig[hardwares.size()]));
		try {
			String selectedHardwareName = config.getAttribute(ATTR_HARDWARE_CONFIG, "");
			for (HardwareConfig hardwareConfig : hardwares) {
				if (selectedHardwareName.equals(hardwareConfig.getName())) {
					final ISelection selection = new StructuredSelection(hardwareConfig);
					fHardwareCombo.setSelection(selection);
				}
			}
			fSimulationCombo.setInput(SimulationType.values());
			String selectedSimulationTypeStr = config.getAttribute(ATTR_SIMULATION_TYPE, "FMU");
			SimulationType selectedSimulationType = SimulationType.valueOf(selectedSimulationTypeStr);
			final ISelection selection = new StructuredSelection(selectedSimulationType);			
			fSimulationCombo.setSelection(selection);
			displaySimulationType();
			
			
			String standAloneTimeIncrement = config.getAttribute(ATTR_STANDALONE_TIME_INCREMENT, "1");
			try{
			fStandAloneTimeIncrement.setSelection(Integer.parseInt(standAloneTimeIncrement));
			} catch (NumberFormatException e){
				fStandAloneTimeIncrement.setSelection(1);
			}
			
			TimeUnit standAloneTimeIncrementUnit = TimeUnit.valueOf(config.getAttribute(ATTR_STANDALONE_TIME_INCREMENT_UNIT, TimeUnit.SECONDS.toString()));
			final ISelection timeIncrementUnitSelection = new StructuredSelection(standAloneTimeIncrementUnit);
			fStandAloneTimeIncrementCombo.setSelection(timeIncrementUnitSelection);
			
			String standAloneTimeRatio = config.getAttribute(ATTR_STANDALONE_TIME_RATIO, "1");
			fStandAloneTimeRatio.setSelection(new Double(Double.parseDouble(standAloneTimeRatio)*1000.0).intValue());	
			
			String standAloneEndTime = config.getAttribute(ATTR_STANDALONE_END_TIME, "60");
			try{
			fStandAloneEndTime.setSelection(Integer.parseInt(standAloneEndTime));
			} catch (NumberFormatException e){
				fStandAloneEndTime.setSelection(60);
			}
			
			TimeUnit standAloneEndTimeUnit = TimeUnit.valueOf(config.getAttribute(ATTR_STANDALONE_END_TIME_UNIT, TimeUnit.SECONDS.toString()));
			final ISelection standAloneEndTimeUnitSelection = new StructuredSelection(standAloneEndTimeUnit);
			fStandAloneEndTimeCombo.setSelection(standAloneEndTimeUnitSelection);
			
			
		} catch (CoreException e) {
			Activator.getDefault().log("Cannot set selected hardware: ", e);
		}			
	}
	
	protected void displaySimulationType() {
		SimulationType selectedSimulationType = (SimulationType)((IStructuredSelection) fSimulationCombo.getSelection()).getFirstElement();

		fStandAloneTimeIncrement.setVisible(SimulationType.STAND_ALONE == selectedSimulationType);
		lStandAloneTimeIncrement.setVisible(SimulationType.STAND_ALONE == selectedSimulationType);
		fStandAloneTimeIncrementCombo.getCombo().setVisible(SimulationType.STAND_ALONE == selectedSimulationType);
		fStandAloneTimeRatio.setVisible(SimulationType.STAND_ALONE == selectedSimulationType);
		lStandAloneTimeRatio.setVisible(SimulationType.STAND_ALONE == selectedSimulationType);
		fStandAloneEndTime.setVisible(SimulationType.STAND_ALONE == selectedSimulationType);
		lStandAloneEndTime.setVisible(SimulationType.STAND_ALONE == selectedSimulationType);
		fStandAloneEndTimeCombo.getCombo().setVisible(SimulationType.STAND_ALONE == selectedSimulationType);

		fgetFMUButton.setVisible(SimulationType.FMU == selectedSimulationType);
	}

}
