package org.raspinloop.fmi.plugin.preferences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.raspinloop.config.HardwareProperties;
import org.raspinloop.config.Pin;

public class PinUsageBlock {

	private Pin[] pins;
	private Composite compositeIO;
	private Button[] inputs;
	private Button[] outputs;
	private Button[] unused;
	private Map<HardwareProperties, Collection<Pin>> pinsByHw = new HashMap<HardwareProperties, Collection<Pin>>();

	public PinUsageBlock(Pin[] pins, String providerName) {
		
		this.pins = pins;
	}

	public void setWizard(IWizard wizard) {
				
	}

	@SuppressWarnings("restriction")
	public void createControl(Composite composite) {
		compositeIO = new Composite(composite, SWT.NONE);	
		GridLayout layoutIO = new GridLayout();
		layoutIO.numColumns = 2;
		compositeIO.setLayout(layoutIO);
		layoutIO.verticalSpacing = 0;
			
		 inputs = new Button[pins.length];
		 outputs = new Button[pins.length];
		 unused = new Button[pins.length];
		for (int i=0; i<pins.length; i++) {								
			SWTFactory.createLabel(compositeIO, pins[i].getName()+": ", 1);		
			Composite buttonGroup = new Composite(compositeIO, SWT.NONE);
			GridLayout buttonGrouplayout = new GridLayout();
			buttonGrouplayout.numColumns = 3;
			buttonGrouplayout.horizontalSpacing = 0;
			buttonGrouplayout.marginHeight = 2;
			buttonGroup.setLayout(buttonGrouplayout);
			buttonGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			inputs[i] = SWTFactory.createRadioButton(buttonGroup,"Input" );
			outputs[i] = SWTFactory.createRadioButton(buttonGroup,"Output" );
			unused[i] = SWTFactory.createRadioButton(buttonGroup,"Unused" );	
			unused[i].setSelection(true); // default state
		}		
	}

	public Control getControl() {
		return compositeIO;
	}

	public void setConfiguredInputPins(Collection<Pin> inputPins) {
		for (Pin pin : inputPins) {
			enableLine(pin.getAddress(), true);	
			inputs[pin.getAddress()].setSelection(true);
			outputs[pin.getAddress()].setSelection(false);
			unused[pin.getAddress()].setSelection(false);
		}
		
	}
	
	public void setConfiguredOutputPins(Collection<Pin> OutputPins) {
		for (Pin pin : OutputPins) {
			enableLine(pin.getAddress(), true);	
			inputs[pin.getAddress()].setSelection(false);
			outputs[pin.getAddress()].setSelection(true);
			unused[pin.getAddress()].setSelection(false);
		}
	}

	public void setConfiguredComponentPins(HardwareProperties hw) {
		
		for (Pin pin : getPreviouslyUsedPinByHw(hw)){
			if (pin != null)
				enableLine(pin.getAddress(), true);	
				unused[pin.getAddress()].setSelection(true);
		}
		
		for (Pin pin : hw.getUsedPins()) {
			if (pin != null)
				enableLine(pin.getAddress(), false);			
		}
		pinsByHw.put(hw, new LinkedList<>(hw.getUsedPins()));
	}
	
	private Collection<Pin> getPreviouslyUsedPinByHw(HardwareProperties hw) {	
		if (pinsByHw.containsKey(hw))
			return pinsByHw.get(hw);
		else
			return Collections.emptyList();
	}

	private void enableLine(int address, boolean b) {
		outputs[address].setEnabled(b);
		inputs[address].setEnabled(b);
		unused[address].setEnabled(b);
		if (b == false) {
			outputs[address].setSelection(false);
			inputs[address].setSelection(false);
			unused[address].setSelection(false);
		}			
	}

	public Collection<Pin> getConfiguredInputPins( ) {
		List<Pin> resultList = new ArrayList<>();
		for (Pin pin : pins) {
			if (inputs[pin.getAddress()].getSelection())
				resultList.add(pin);
		}
		return Collections.<Pin>unmodifiableCollection(resultList);
	}
	
	public Collection<Pin> getConfiguredOutputPins() {
		List<Pin> resultList = new ArrayList<>();
		for (Pin pin : pins) {
			if (outputs[pin.getAddress()].getSelection())
				resultList.add(pin);
		}
		return Collections.<Pin>unmodifiableCollection(resultList);
	}

	

}
