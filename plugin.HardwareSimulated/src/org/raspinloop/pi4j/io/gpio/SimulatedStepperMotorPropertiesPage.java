package org.raspinloop.pi4j.io.gpio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.raspinloop.config.HardwareConfig;
import org.raspinloop.config.Pin;
import org.raspinloop.config.PinState;
import org.raspinloop.fmi.plugin.Images;
import org.raspinloop.fmi.plugin.preferences.extension.AbstractHWConfigPage;
import org.raspinloop.pi4j.io.gpio.SimulatedStepperMotorProperties.StepSequence;


public class SimulatedStepperMotorPropertiesPage extends AbstractHWConfigPage {

	public class PinLabelProvider extends LabelProvider {
		@Override
		  public String getText(Object element) {
		    if (element instanceof Pin) {
		    	Pin pin = (Pin) element;
		      return pin.getName();
		    }
		    return super.getText(element);
		  }
	}

	public class AvaillablePinProvider implements IStructuredContentProvider {
		
		public AvaillablePinProvider() {
		}

		@Override
		public void dispose() {
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@SuppressWarnings("unchecked")
		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof Collection<?>){
				
				 ArrayList<Pin> arrayList = new ArrayList<Pin>((Collection<? extends Pin>)inputElement);
				//Sorting
				final NaturalOrderComparator comparator = new NaturalOrderComparator();
				Collections.sort(arrayList, new Comparator<Pin>() {
				        @Override
				        public int compare(Pin  pin1, Pin  pin2)
				        {
				        	return comparator.compare(pin1.getName(), pin2.getName());
				        }
				    });
				
				return arrayList.toArray();
			}
			return null;
		}
	}

	private IStatus[] fFieldStatus = new IStatus[1];
	private Text fHWName;
	private SimulatedStepperMotorProperties fHW;
	private Spinner fHWStepsPerRotation;
	private Spinner fHWInitialPosition;
	private Combo fHWOnState;
	private ComboViewer [] fHWPins = new ComboViewer [4];
	private Combo fHWStepSequence;

	public SimulatedStepperMotorPropertiesPage() {
		super("Simulated StepperMotor configuration");
		for (int i = 0; i < fFieldStatus.length; i++) {
			fFieldStatus[i] = Status.OK_STATUS;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
	public void createControl(Composite p) {
		// create a composite with standard margins and spacing
		Composite composite = new Composite(p, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		SWTFactory.createLabel(composite, "Stepper Motor Name", 1);
		fHWName = SWTFactory.createSingleText(composite, 1);

		addLabel(composite, "Step by Rotation");

		fHWStepsPerRotation = new Spinner(composite, SWT.NONE);
		fHWStepsPerRotation.setDigits(0);
		fHWStepsPerRotation.setMinimum(0);
		fHWStepsPerRotation.setMaximum(400);
		fHWStepsPerRotation.setIncrement(1);
		fHWStepsPerRotation.setSelection(200);

		addLabel(composite, "Initial Position (°)");
		fHWInitialPosition = new Spinner(composite, SWT.NONE);
		fHWInitialPosition.setDigits(1);
		fHWInitialPosition.setMinimum(0);
		fHWInitialPosition.setMaximum(3600);
		fHWInitialPosition.setIncrement(1);
		fHWInitialPosition.setSelection(72);

		addLabel(composite, "ON state");
		fHWOnState = new Combo(composite, SWT.READ_ONLY);
		fHWOnState.setItems(new String[] { PinState.HIGH.toString(), PinState.LOW.toString() });

		addLabel(composite, "Step Sequence");
		fHWStepSequence = new Combo(composite, SWT.READ_ONLY);
		fHWStepSequence.setItems(new String[] { SimulatedStepperMotorProperties.StepSequence.SINGLE_STEP.toString(),
				SimulatedStepperMotorProperties.StepSequence.DOUBLE_STEP.toString(), SimulatedStepperMotorProperties.StepSequence.HALF_STEP.toString() });
		addLabel(composite, "Connected Pin");
		ArrayList<Pin> usablePins = new ArrayList<Pin>(fHW.getParentComponent().getUnUsedPins());
		usablePins.addAll(fHW.getUsedPins());
		
		Composite pinBlockComposite = new Composite(composite, SWT.NONE);
		GridLayout pinBlockLayout = new GridLayout();
		pinBlockLayout.numColumns = 4;
		pinBlockLayout.marginLeft=0;
		pinBlockComposite.setLayout(pinBlockLayout);
		pinBlockComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		for (int i = 0; i < fHWPins.length; i++) {
			ComboViewer  hWPin = new ComboViewer (pinBlockComposite, SWT.READ_ONLY);
			hWPin.setContentProvider(new AvaillablePinProvider());
			hWPin.setLabelProvider(new PinLabelProvider());	
			
			hWPin.setInput(usablePins);
			fHWPins[i] = hWPin;
		}

		
		// add the listeners now to prevent them from monkeying with initialized
		// settings
		fHWName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				nameChanged(fHWName.getText());
			}
		});

		Dialog.applyDialogFont(composite);
		setControl(composite);
		initializeFields();
	}

	private void addLabel(Composite composite, String text) {
		Label l = new Label(composite, SWT.NONE);
		l.setFont(composite.getFont());
		l.setText(text);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessHorizontalSpace = false;
		gd.verticalAlignment = SWT.BEGINNING;
		l.setLayoutData(gd);
	}


	/**
	 * Initialize the dialogs fields
	 */
	private void initializeFields() {
		fHWName.setText(fHW.getName());
		Pin[] pins = fHW.getUsedPins().toArray(new Pin[0]);
		for (int j = 0; j < pins.length; j++) {
			fHWPins[j].setSelection(new StructuredSelection(pins[j]));
		}

		fHWStepsPerRotation.setSelection(fHW.getStepsPerRotation());
		fHWInitialPosition.setSelection((int) Math.round(fHW.getInitalPosition() * 10));
		fHWOnState.setText(fHW.getOnState().toString());
		fHWStepSequence.setText(fHW.getStepSequence().toString());

		nameChanged(fHWName.getText());
	}

	private void setFieldValuesToHW() {
		fHW.setName(fHWName.getText());
		ArrayList<Pin> pins2 = new ArrayList<>(4);
		for (ComboViewer comboPin : fHWPins) {
			IStructuredSelection  selection = (IStructuredSelection) comboPin.getSelection();
			Pin pin = (Pin) selection.getFirstElement();
			if (pin != null)				
				pins2.add(pin);	
		}
		fHW.setPins(pins2);			

		fHW.setInitalPosition(fHWInitialPosition.getSelection() / 10.0);
		fHW.setOnState(PinState.valueOf(fHWOnState.getText()));
		if (fHW.getOnState().equals(PinState.HIGH))
			fHW.setOffState(PinState.LOW);
		else
			fHW.setOffState(PinState.HIGH);
		fHW.setStepsPerRotation(fHWStepsPerRotation.getSelection());
		fHW.setStepSequence(StepSequence.valueOf(fHWStepSequence.getText()));
	}	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#getImage()
	 */
	@Override
	public Image getImage() {
		return Images.get(Images.IMG_HARDWARE);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.debug.ui.launchConfigurations.AbstractVMInstallPage#
	 * getVMStatus()
	 */
	@Override
	protected IStatus[] getHWStatus() {
		return fFieldStatus;
	}

	@Override
	public boolean finish() {
		setFieldValuesToHW();
		return true;
	}
	
	@Override
	public void setSelection(HardwareConfig hw) {
		super.setSelection(hw);
		if (hw instanceof SimulatedStepperMotorProperties)
			fHW = (SimulatedStepperMotorProperties)hw;		
		setTitle("Configure Stepper Motor");
		setDescription("Use this page to configure your Simulated Stepper Motor.");
	}

	@Override
	public HardwareConfig getSelection() {
		return fHW;
	}
}
