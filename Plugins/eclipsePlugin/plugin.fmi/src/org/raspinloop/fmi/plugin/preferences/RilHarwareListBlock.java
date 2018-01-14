package org.raspinloop.fmi.plugin.preferences;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.jdt.internal.debug.ui.JDIDebugUIPlugin;
import org.eclipse.jdt.internal.debug.ui.jres.IAddVMDialogRequestor;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.statushandlers.StatusManager;
import org.raspinloop.config.AlreadyUsedPin;
import org.raspinloop.config.BoardExtentionHardware;
import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.GPIOHardware;
import org.raspinloop.config.HardwareProperties;
import org.raspinloop.config.I2CComponent;
import org.raspinloop.config.I2CParent;
import org.raspinloop.config.SPIComponent;
import org.raspinloop.config.SPIParent;
import org.raspinloop.config.UARTComponent;
import org.raspinloop.config.UARTParent;
import org.raspinloop.fmi.plugin.Activator;

/**
 * A composite that displays hardware config in a table. hardwares can be added,
 * removed, edited, and searched for.
 * <p>
 * This block implements ISelectionProvider - it sends selection change events
 * when the checked Hardware in the table changes, or when the "use default"
 * button check state changes.
 * </p>
 */
@SuppressWarnings("restriction")
public class RilHarwareListBlock implements IAddHardwareDialogRequestor, ISelectionProvider {

	/**
	 * This block's control
	 */
	private Composite control;

	/**
	 * VMs being displayed
	 */
	private List<HardwareProperties> hardwares = new ArrayList<HardwareProperties>();

	private List<Class<? extends HardwareProperties>> supportedHwChildrenTypes;
	/**
	 * The main list control
	 */
	private TableViewer hardwareList;

	// Action buttons
	private Button addButton;
	private Button removeButton;
	private Button editButton;
	private Button copyButton;
	private Button exportButton;

	// index of column used for sorting
	private int sortColumn = 0;

	/**
	 * Selection listeners (checked HW changes)
	 */
	private ListenerList<ISelectionChangedListener> selectionListeners = new ListenerList<>();

	/**
	 * Add new Hardware listener
	 */
	private ListenerList<IHWListener> addHWListeners = new ListenerList<>();

	/**
	 * Remove Hardware listener
	 */
	private ListenerList<IHWListener> deleteHWListeners = new ListenerList<>();

	/**
	 * Update Hardware listener
	 */
	private ListenerList<IHWListener> updateHWListeners = new ListenerList<>();


	/**
	 * Expport Hardware listener
	 */
	private ListenerList<IHWListener> exportHWListeners = new ListenerList<>();

	private Table table;
	// private ArrayList<HardwareConfig> supportedHardwareDefs = new
	// ArrayList<HardwareConfig>();

	private boolean editAfterAdd = false;

	private HardwareProperties parentHw;

	/**
	 * Content provider to show a list of hardware
	 */
	class HwsContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object input) {
			return hardwares.toArray();
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		public void dispose() {
		}
	}

	/**
	 * Label provider for installed hardware table.
	 */
	class HWLabelProvider extends LabelProvider implements ITableLabelProvider, IFontProvider, IColorProvider {

		Font bold = null;

		/**
		 * @see ITableLabelProvider#getColumnText(Object, int)
		 */
		public String getColumnText(Object element, int columnIndex) {
			if (element instanceof HardwareProperties) {
				HardwareProperties hw = (HardwareProperties) element;
				switch (columnIndex) {
				case 0:
					if (isBuiltIn(hw)) {
						return hw.getComponentName() + " (built-in)";
					}
					return hw.getComponentName();

				case 1:
					StringBuilder desc = new StringBuilder();
					if (hw instanceof GPIOHardware)
						desc.append("Inputs:" + ((GPIOHardware) hw).getInputPins().size() + " Outputs:" + ((GPIOHardware) hw).getOutputPins().size());
					if (hw instanceof BoardHardware)
						desc.append(" Components:" + ((BoardHardware) hw).getAllComponents().size());
					if (desc.toString().isEmpty())
						return "no information available";
					else
						return desc.toString();

				case 2:

					return hw.getSimulatedProviderName();
				}
			}
			return element.toString();
		}

		/**
		 * @see ITableLabelProvider#getColumnImage(Object, int)
		 */
		public Image getColumnImage(Object element, int columnIndex) {
			// TODO find image based on implementation className
			return null;
		}

		public Font getFont(Object element) {
			if (element instanceof HardwareProperties) {
				HardwareProperties hw = (HardwareProperties) element;
				if (isBuiltIn(hw)) {
					if (bold == null) {
						Font dialogFont = JFaceResources.getDialogFont();
						FontData[] fontData = dialogFont.getFontData();
						for (int i = 0; i < fontData.length; i++) {
							FontData data = fontData[i];
							data.setStyle(SWT.BOLD);
						}
						Display display = JDIDebugUIPlugin.getStandardDisplay();
						bold = new Font(display, fontData);
					}
					return bold;
				}
			}
			return null;
		}

		@Override
		public void dispose() {
			if (bold != null) {
				bold.dispose();
			}
			super.dispose();
		}

		public Color getForeground(Object element) {
			if (isUnmodifiable(element)) {
				Display display = Display.getCurrent();
				return display.getSystemColor(SWT.COLOR_INFO_FOREGROUND);
			}
			return null;
		}

		public Color getBackground(Object element) {
			if (isUnmodifiable(element)) {
				Display display = Display.getCurrent();
				return display.getSystemColor(SWT.COLOR_INFO_BACKGROUND);
			}
			return null;
		}

		boolean isUnmodifiable(Object element) {
			if (element instanceof HardwareProperties) {
				HardwareProperties hw = (HardwareProperties) element;
				return isBuiltIn(hw);
			}
			return false;
		}

	}

	public RilHarwareListBlock(final HardwareProperties parentHw, List<Class<? extends HardwareProperties>> list) {
		// parent must be a type having component
		// TODO: this class should not have to known such information
		if ((parentHw instanceof BoardHardware) || (parentHw instanceof I2CParent) || (parentHw instanceof SPIParent) || (parentHw instanceof UARTParent))
			this.parentHw = parentHw;
		// list of children type
		this.supportedHwChildrenTypes = list;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ISelectionProvider#addSelectionChangedListener
	 * (org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionListeners.add(listener);
	}

	public boolean isBuiltIn(HardwareProperties hw) {
		return false;
	}

	public void addAddHWListener(IHWListener listener) {
		addHWListeners.add(listener);
	}

	public void addDeleteHWListener(IHWListener listener) {
		deleteHWListeners.add(listener);
	}

	public void addEditHWListener(IHWListener listener) {
		updateHWListeners.add(listener);
	}

	public void removeAddHWListener(IHWListener listener) {
		addHWListeners.remove(listener);
	}

	public void removeDeleteHWListener(IHWListener listener) {
		deleteHWListeners.remove(listener);
	}

	public void removeEditHWListener(IHWListener listener) {
		updateHWListeners.remove(listener);
	}

	public void addExportHWListener(IHWListener listener) {
		exportHWListeners.add(listener);

	}

	public void removeExportHWListener(IHWListener listener) {
		exportHWListeners.remove(listener);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ISelectionProvider#getSelection()
	 */
	public ISelection getSelection() {
		return new StructuredSelection(hardwareList.getSelection());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ISelectionProvider#
	 * removeSelectionChangedListener
	 * (org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		selectionListeners.remove(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ISelectionProvider#setSelection(org.eclipse
	 * .jface.viewers.ISelection)
	 */
	public void setSelection(ISelection selection) {

	}

	/**
	 * Creates this block's control in the given control.
	 * 
	 * @param ancestor
	 *            containing control
	 * @param supportedHardwareDefs
	 * @param useManageButton
	 *            whether to present a single 'manage...' button to the user
	 *            that opens the installed JREs pref page for JRE management, or
	 *            to provide 'add, remove, edit, and search' buttons.
	 */

	public void createControl(Composite ancestor) {
		Font font = ancestor.getFont();
		Composite parent = SWTFactory.createComposite(ancestor, font, 2, 1, GridData.FILL_BOTH);
		control = parent;

		SWTFactory.createLabel(parent, "Existing Simulated Hardware", 2);

		table = new Table(parent, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 250;
		gd.widthHint = 350;
		table.setLayoutData(gd);
		table.setFont(font);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn column = new TableColumn(table, SWT.NULL);
		column.setText("Name");
		column.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sortByName();
				hardwareList.refresh(true);
			}
		});
		column.setWidth(200);

		column = new TableColumn(table, SWT.NULL);
		column.setText("Usage description");
		column.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sortByType();
				hardwareList.refresh(true);
			}
		});
		column.setWidth(100);

		column = new TableColumn(table, SWT.NULL);
		column.setText("Simulated provider name");
		column.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sortByLocation();
				hardwareList.refresh(true);
			}
		});
		column.setWidth(50);

		hardwareList = new TableViewer(table);
		hardwareList.setLabelProvider(new HWLabelProvider());
		hardwareList.setContentProvider(new HwsContentProvider());
		hardwareList.setUseHashlookup(true);
		// by default, sort by name
		sortByName();

		hardwareList.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent evt) {
				enableButtons();
			}
		});

		hardwareList.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent e) {
				if (!hardwareList.getSelection().isEmpty()) {
					editHW();
				}
			}
		});
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent event) {
				if (event.character == SWT.DEL && event.stateMask == 0) {
					if (removeButton.isEnabled()) {
						removeHWs(parentHw);
					}
				}
			}
		});

		Composite buttons = SWTFactory.createComposite(parent, font, 1, 1, GridData.VERTICAL_ALIGN_BEGINNING, 0, 0);

		addButton = SWTFactory.createPushButton(buttons, "Add...", null);
		addButton.addListener(SWT.Selection, new Listener() {

			public void handleEvent(Event evt) {
				try {
					addHW(parentHw, supportedHwChildrenTypes);
				} catch (InstantiationException | IllegalAccessException | AlreadyUsedPin e) {
					IStatus status = new Status(1, Activator.PLUGIN_ID, e.getMessage());
					StatusManager.getManager().handle(status, StatusManager.LOG | StatusManager.SHOW);
				}
			}
		});

		editButton = SWTFactory.createPushButton(buttons, "Edit...", null);
		editButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				editHW();
			}
		});

		copyButton = SWTFactory.createPushButton(buttons, "Duplicate...", null);
		copyButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				try {
					copyHW(parentHw);
				} catch (IllegalAccessException | InstantiationException | AlreadyUsedPin e) {
					IStatus status = new Status(1, Activator.PLUGIN_ID, e.getMessage());
					StatusManager.getManager().handle(status, StatusManager.LOG | StatusManager.SHOW);
				}
			}
		});

		removeButton = SWTFactory.createPushButton(buttons, "Remove", null);
		removeButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				removeHWs(parentHw);
			}
		});


		exportButton = SWTFactory.createPushButton(buttons, "Export", null);
		exportButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event evt) {
				exportHWs(parentHw);
			}
		});
		enableButtons();
		addButton.setEnabled(true);
	}

	/**
	 * Fill the table with hardwares
	 * 
	 * @param hws
	 */
	public void setHwList(List<HardwareProperties> hws) {
		hardwares = hws;
		hardwareList.setInput(hardwares);
		control.layout(new Control[] { hardwareList.getControl() });
		hardwareList.refresh();
	}

	/**
	 * Adds a duplicate of the selected VM to the block
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws AlreadyUsedPin
	 * 
	 * @since 3.2
	 */
	protected void copyHW(HardwareProperties parent) throws InstantiationException, IllegalAccessException, AlreadyUsedPin {
		IStructuredSelection selection = (IStructuredSelection) hardwareList.getSelection();
		@SuppressWarnings("unchecked")
		Iterator<HardwareProperties> it = selection.iterator();

		ArrayList<HardwareProperties> newEntries = new ArrayList<HardwareProperties>();
		while (it.hasNext()) {
			HardwareProperties selectedHW = it.next();
			// duplicate & add HW
			HardwareProperties newHardware = selectedHW.getClass().newInstance();
			newHardware.setComponentName(generateName(selectedHW.getComponentName(), getDisplayNames()));
			linkComponent(parent, newHardware);

			EditHwWizard wizard = new EditHwWizard(newHardware, hardwares.toArray(new HardwareProperties[hardwares.size()]));
			WizardDialog dialog = new WizardDialog(getShell(), wizard);
			int dialogResult = dialog.open();
			if (dialogResult == Window.OK) {
				HardwareProperties result = wizard.getResult();
				if (result != null) {
					newEntries.add(result);
				}
			} else if (dialogResult == Window.CANCEL) {
				// Canceling one wizard should cancel all subsequent wizards
				break;
			}
		}
		if (newEntries.size() > 0) {
			hardwares.addAll(newEntries);
			for (Object listenerObj : addHWListeners.getListeners()) {
				if (listenerObj instanceof IHWListener) {
					IHWListener listerner = (IHWListener) listenerObj;
					for (HardwareProperties configurableHardware : newEntries) {
						listerner.addOrRemoveHW(configurableHardware);
					}
				}
			}
			hardwareList.refresh();
			hardwareList.setSelection(new StructuredSelection(newEntries.toArray()));
		} else {
			hardwareList.setSelection(selection);
		}
		hardwareList.refresh(true);
	}

	/**
	 * Compares the given name against current names and adds the appropriate
	 * numerical suffix to ensure that it is unique.
	 * 
	 * @param name
	 *            the name with which to ensure uniqueness
	 * @return the unique version of the given name
	 * @since 3.2
	 */
	public String generateName(String name, List<String> existingName) {
		if (!isDuplicateName(name, existingName)) {
			return name;
		}
		if (name.matches(".*\\(\\d*\\)")) { //$NON-NLS-1$
			int start = name.lastIndexOf('(');
			int end = name.lastIndexOf(')');
			String stringInt = name.substring(start + 1, end);
			int numericValue = Integer.parseInt(stringInt);
			String newName = name.substring(0, start + 1) + (numericValue + 1) + ")"; //$NON-NLS-1$
			return generateName(newName, existingName);
		}
		return generateName(name + " (1)", existingName); //$NON-NLS-1$
	}

	/**
	 * Fire current selection
	 */
	private void fireSelectionChanged() {
		SelectionChangedEvent event = new SelectionChangedEvent(this, getSelection());
		Object[] listeners = selectionListeners.getListeners();
		for (int i = 0; i < listeners.length; i++) {
			ISelectionChangedListener listener = (ISelectionChangedListener) listeners[i];
			listener.selectionChanged(event);
		}
	}

	/**
	 * Sorts by VM type, and name within type.
	 */
	private void sortByType() {
		hardwareList.setComparator(new ViewerComparator() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				if ((e1 instanceof IVMInstall) && (e2 instanceof IVMInstall)) {
					IVMInstall left = (IVMInstall) e1;
					IVMInstall right = (IVMInstall) e2;
					String leftType = left.getVMInstallType().getName();
					String rightType = right.getVMInstallType().getName();
					int res = leftType.compareToIgnoreCase(rightType);
					if (res != 0) {
						return res;
					}
					return left.getName().compareToIgnoreCase(right.getName());
				}
				return super.compare(viewer, e1, e2);
			}

			@Override
			public boolean isSorterProperty(Object element, String property) {
				return true;
			}
		});
		sortColumn = 3;
	}

	/**
	 * Sorts by VM name.
	 */
	private void sortByName() {
		hardwareList.setComparator(new ViewerComparator() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				if ((e1 instanceof IVMInstall) && (e2 instanceof IVMInstall)) {
					IVMInstall left = (IVMInstall) e1;
					IVMInstall right = (IVMInstall) e2;
					return left.getName().compareToIgnoreCase(right.getName());
				}
				return super.compare(viewer, e1, e2);
			}

			@Override
			public boolean isSorterProperty(Object element, String property) {
				return true;
			}
		});
		sortColumn = 1;
	}

	/**
	 * Sorts by VM location.
	 */
	private void sortByLocation() {
		hardwareList.setComparator(new ViewerComparator() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				if ((e1 instanceof IVMInstall) && (e2 instanceof IVMInstall)) {
					IVMInstall left = (IVMInstall) e1;
					IVMInstall right = (IVMInstall) e2;
					return left.getInstallLocation().getAbsolutePath().compareToIgnoreCase(right.getInstallLocation().getAbsolutePath());
				}
				return super.compare(viewer, e1, e2);
			}

			@Override
			public boolean isSorterProperty(Object element, String property) {
				return true;
			}
		});
		sortColumn = 2;
	}

	/**
	 * Enables the buttons based on selected items counts in the viewer
	 */
	private void enableButtons() {
		IStructuredSelection selection = (IStructuredSelection) hardwareList.getSelection();
		int selectionCount = selection.size();

		copyButton.setEnabled(selectionCount > 0);
		if (selectionCount > 0 && selectionCount <= hardwareList.getTable().getItemCount()) {
			@SuppressWarnings("unchecked")
			Iterator<HardwareProperties> iterator = selection.iterator();
			while (iterator.hasNext()) {
				HardwareProperties hardware = iterator.next();
				if (isBuiltIn(hardware)) {
					removeButton.setEnabled(false);
					exportButton.setEnabled(false);
					editButton.setEnabled(false);
					return;
				}
			}
			removeButton.setEnabled(true);
			exportButton.setEnabled(true);
			editButton.setEnabled(selectionCount == 1);
		} else {
			editButton.setEnabled(false);
			removeButton.setEnabled(false);
			exportButton.setEnabled(false);
		}
	}

	/**
	 * Returns this block's control
	 * 
	 * @return control
	 */
	public Control getControl() {
		return control;
	}

	/**
	 * Returns the Hardware currently being displayed in this block
	 * 
	 * @return Hardware currently being displayed in this block
	 */
	public HardwareProperties[] getHWs() {
		return hardwares.toArray(new HardwareProperties[hardwares.size()]);
	}

	/**
	 * Bring up a wizard that lets the user create a new HD definition.
	 * 
	 * @param hwType
	 *            TODO
	 * 
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws AlreadyUsedPin
	 */
	private void addHW(HardwareProperties parent, List<Class<? extends HardwareProperties>> hwType)
			throws InstantiationException, IllegalAccessException, AlreadyUsedPin {
		// Select a type in list

		HardwareSelectionDialog hdsd = new HardwareSelectionDialog(getShell(), hwType);
		if (hdsd.open() == Window.CANCEL) {
			return;
		}
		Object[] results = hdsd.getResult();
		if (results.length >= 1 && results[0] instanceof HardwareProperties) {
			HardwareProperties hWDef = (HardwareProperties) results[0];
			String name = generateName(hWDef.getComponentName(), getNames());

			HardwareProperties newHardware = hWDef.getClass().newInstance();
			newHardware.setComponentName(name);

			linkComponent(parent, newHardware);

			if (editAfterAdd) {
				// then edit it
				EditHwWizard wizard = new EditHwWizard(newHardware, hardwares.toArray(new HardwareProperties[hardwares.size()]));
				WizardDialog dialog = new WizardDialog(getShell(), wizard);
				int dialogResult = dialog.open();
				HardwareProperties result = wizard.getResult();
				if (dialogResult == Window.OK && result != null) {
					hardwares.add(result);
					for (Object listenerObj : addHWListeners.getListeners()) {
						if (listenerObj instanceof IHWListener) {
							IHWListener listerner = (IHWListener) listenerObj;
							listerner.addOrRemoveHW(result);
						}
					}
					hardwareList.refresh();
					hardwareList.setSelection(new StructuredSelection(result));

				}
			} else {
				hardwares.add(newHardware);
				for (Object listenerObj : addHWListeners.getListeners()) {
					if (listenerObj instanceof IHWListener) {
						IHWListener listerner = (IHWListener) listenerObj;
						listerner.addOrRemoveHW(newHardware);
					}
				}
				hardwareList.refresh();
				hardwareList.setSelection(new StructuredSelection(newHardware));
			}
			// ensure labels are updated
			hardwareList.refresh(true);
		}
	}

	/**
	 * @see IAddVMDialogRequestor#vmAdded(IVMInstall)
	 */
	public void hwAdded(HardwareProperties hw) {
		boolean makeselection = hardwares.size() < 1;
		hardwares.add(hw);
		// update from model
		hardwareList.refresh();
		// update labels
		hardwareList.refresh(true);
		// if we add a VM and none are selected, select one of them
		if (makeselection) {
			fireSelectionChanged();
		}
	}

	/**
	 * @see IAddVMDialogRequestor#isDuplicateName(String)
	 */
	public boolean isDuplicateName(String name, List<String> names) {
		for (String string : names) {
			if (string.equals(name)) {
				return true;
			}
		}
		return false;
	}

	private List<String> getNames() {
		LinkedList<String> result = new LinkedList<String>();
		for (int i = 0; i < hardwares.size(); i++) {
			HardwareProperties hw = hardwares.get(i);
			result.add(hw.getType());
		}
		return result;
	}

	private List<String> getDisplayNames() {
		LinkedList<String> result = new LinkedList<String>();
		for (int i = 0; i < hardwares.size(); i++) {
			HardwareProperties hw = hardwares.get(i);
			result.add(hw.getComponentName());
		}
		return result;
	}

	/**
	 * Performs the edit VM action when the Edit... button is pressed
	 */
	private void editHW() {
		IStructuredSelection selection = (IStructuredSelection) hardwareList.getSelection();
		@SuppressWarnings("unchecked")
		Iterator<HardwareProperties> it = selection.iterator();
		if (it.hasNext()) {
			HardwareProperties selectedHW = it.next();
			EditHwWizard wizard = new EditHwWizard(selectedHW, hardwares.toArray(new HardwareProperties[hardwares.size()]));
			WizardDialog dialog = new WizardDialog(getShell(), wizard);
			int dialogResult = dialog.open();
			HardwareProperties result = wizard.getResult();
			if (dialogResult == Window.OK && result != null) {

				for (Object listenerObj : updateHWListeners.getListeners()) {
					if (listenerObj instanceof IHWListener) {
						IHWListener listerner = (IHWListener) listenerObj;
						listerner.addOrRemoveHW(result);
					}
				}
			}
		}

		hardwareList.refresh(true);
	}

	/**
	 * Performs the remove VM(s) action when the Remove... button is pressed
	 */
	private void removeHWs(HardwareProperties parent) {
		IStructuredSelection selection = (IStructuredSelection) hardwareList.getSelection();
		HardwareProperties[] hws = new HardwareProperties[selection.size()];
		@SuppressWarnings("unchecked")
		Iterator<HardwareProperties> iter = selection.iterator();
		int i = 0;
		while (iter.hasNext()) {
			hws[i] = iter.next();
			i++;
		}
		removeHWs(hws, parent);
	}

	/**
	 * Performs the export VM(s) action when the Remove... button is pressed
	 */
	private void exportHWs(HardwareProperties parent) {
		IStructuredSelection selection = (IStructuredSelection) hardwareList.getSelection();
		HardwareProperties[] hws = new HardwareProperties[selection.size()];
		@SuppressWarnings("unchecked")
		Iterator<HardwareProperties> iter = selection.iterator();
		int i = 0;
		while (iter.hasNext()) {
			hws[i] = iter.next();
			i++;
		}
		exportHWs(hws, parent);
	}

	/**
	 * Removes the given VMs from the table.
	 * 
	 * @param hws
	 */
	public void exportHWs(HardwareProperties[] hws, HardwareProperties parent) {
		for (int i = 0; i < hws.length; i++) {
			for (Object listenerObj : exportHWListeners.getListeners()) {
				if (listenerObj instanceof IHWListener) {
					IHWListener listerner = (IHWListener) listenerObj;
					listerner.addOrRemoveHW(hws[i]);
				}
			}
		}
	}

	/**
	 * Removes the given VMs from the table.
	 * 
	 * @param hws
	 */
	public void removeHWs(HardwareProperties[] hws, HardwareProperties parent) {
		for (int i = 0; i < hws.length; i++) {
			removeFromParent(parent, hws[i]);
			hardwares.remove(hws[i]);

			for (Object listenerObj : deleteHWListeners.getListeners()) {
				if (listenerObj instanceof IHWListener) {
					IHWListener listerner = (IHWListener) listenerObj;
					listerner.addOrRemoveHW(hws[i]);
				}
			}
		}
		hardwareList.refresh();
		IStructuredSelection curr = (IStructuredSelection) getSelection();
		HardwareProperties[] installs = getHWs();
		if (curr.size() == 0 && installs.length == 1) {
			// pick a default HD automatically
			setSelection(new StructuredSelection(installs[0]));
		} else {
			fireSelectionChanged();
		}
		hardwareList.refresh(true);
	}

	private void removeFromParent(HardwareProperties parent, HardwareProperties component) {
		// TODO: this class should not have to known such information
		if ((component instanceof BoardExtentionHardware) && (parent instanceof BoardHardware))
			((BoardHardware) parent).removeComponent((BoardExtentionHardware) component);
		else if ((component instanceof I2CComponent) && (parent instanceof I2CParent))
			((I2CParent) parent).removeComponent((I2CComponent) component);
		else if ((component instanceof SPIComponent) && (parent instanceof SPIParent))
			((SPIParent) parent).removeComponent((SPIComponent) component);
		else if ((component instanceof UARTComponent) && (parent instanceof UARTParent))
			((UARTParent) parent).removeComponent((UARTComponent) component);
	}

	private void linkComponent(HardwareProperties parent, HardwareProperties component) throws AlreadyUsedPin {
		// TODO: this class should not have to known such information
		if ((component instanceof BoardExtentionHardware) && (parent instanceof BoardHardware)) {
			BoardHardware board = (BoardHardware) parent;
			BoardExtentionHardware extention = (BoardExtentionHardware) component;
			board.addComponent(extention);
			extention.setParent(board);
		} else if ((component instanceof I2CComponent) && (parent instanceof I2CParent)) {
			I2CParent board = (I2CParent) parent;
			I2CComponent extention = (I2CComponent) component;
			board.addComponent(extention);
			extention.setParent(board);
		} else if ((component instanceof SPIComponent) && (parent instanceof SPIParent)) {
			SPIParent board = (SPIParent) parent;
			SPIComponent extention = (SPIComponent) component;
			board.addComponent(extention);
			extention.setParent(board);
		} else if ((component instanceof UARTComponent) && (parent instanceof UARTParent)) {
			UARTParent board = (UARTParent) parent;
			UARTComponent extention = (UARTComponent) component;
			board.addComponent(extention);
			extention.setParent(board);
		}
	}

	protected Shell getShell() {
		return getControl().getShell();
	}

	/**
	 * Persist table settings into the give dialog store, prefixed with the
	 * given key.
	 * 
	 * @param settings
	 *            dialog store
	 * @param qualifier
	 *            key qualifier
	 */
	public void saveColumnSettings(IDialogSettings settings, String qualifier) {
		int columnCount = table.getColumnCount();
		for (int i = 0; i < columnCount; i++) {
			settings.put(qualifier + ".columnWidth" + i, table.getColumn(i).getWidth()); //$NON-NLS-1$
		}
		settings.put(qualifier + ".sortColumn", sortColumn); //$NON-NLS-1$
	}

	/**
	 * Restore table settings from the given dialog store using the given key.
	 * 
	 * @param settings
	 *            dialog settings store
	 * @param qualifier
	 *            key to restore settings from
	 */
	public void restoreColumnSettings(IDialogSettings settings, String qualifier) {
		hardwareList.getTable().layout(true);
		restoreColumnWidths(settings, qualifier);
		try {
			sortColumn = settings.getInt(qualifier + ".sortColumn"); //$NON-NLS-1$
		} catch (NumberFormatException e) {
			sortColumn = 1;
		}
		switch (sortColumn) {
		case 1:
			sortByName();
			break;
		case 2:
			sortByLocation();
			break;
		case 3:
			sortByType();
			break;
		}
	}

	/**
	 * Restores the column widths from dialog settings
	 * 
	 * @param settings
	 * @param qualifier
	 */
	private void restoreColumnWidths(IDialogSettings settings, String qualifier) {
		int columnCount = table.getColumnCount();
		for (int i = 0; i < columnCount; i++) {
			int width = -1;
			try {
				width = settings.getInt(qualifier + ".columnWidth" + i); //$NON-NLS-1$
			} catch (NumberFormatException e) {
			}

			if ((width <= 0) || (i == table.getColumnCount() - 1)) {
				table.getColumn(i).pack();
			} else {
				table.getColumn(i).setWidth(width);
			}
		}
	}

	public boolean isEditAfterAdd() {
		return editAfterAdd;
	}

	public void setEditAfterAdd(boolean editAfterAdd) {
		this.editAfterAdd = editAfterAdd;
	}

	public void setWizard(IWizard wizard) {
	}

}
