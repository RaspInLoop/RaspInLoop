/*******************************************************************************
 * Copyright (C) 2018 RaspInLoop
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.raspinloop.pi4j.io.i2c;

import java.util.EnumSet;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.raspinloop.config.HardwareProperties;
import org.raspinloop.fmi.plugin.preferences.extension.AbstractHWConfigPage;
import org.raspinloop.pi4j.io.i2c.TSL256xProperties.AddressSelectionPin;

public class TSL256xPropertiesPage extends AbstractHWConfigPage {

	public class AddressLabelProvided extends LabelProvider {
		@Override
		public String getText(Object element) {
			return element.toString();
		}
	}

	private IStatus[] fFieldStatus = new IStatus[1];
	private TSL256xProperties fHW;

	private Text fHWName;
	private ComboViewer fHWAddress;
	private Spinner fHWirRation;
	private Label fHwBus;
	private Label fHwPackageType;
	private Label fHwRevnumber;

	public TSL256xPropertiesPage() {
		super("TSL2561");
		for (int i = 0; i < fFieldStatus.length; i++) {
			fFieldStatus[i] = Status.OK_STATUS;
		}
	}

	@Override
	public void createControl(Composite parent) {
		// create a composite with standard margins and spacing
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		addLabel(composite, "TSL2561 Name");
		fHWName = addText(composite);

		addLabel(composite, "Selected Address");
		fHWAddress = new ComboViewer(composite, SWT.READ_ONLY);
		fHWAddress.setContentProvider(ArrayContentProvider.getInstance());
		fHWAddress.setInput(EnumSet.allOf(AddressSelectionPin.class).toArray());
		IBaseLabelProvider addressLabelProvider = new AddressLabelProvided();
		fHWAddress.setLabelProvider(addressLabelProvider);

		addLabel(composite, "Use fixed irRatio (CH1/CH0");
		fHWirRation = new Spinner(composite, SWT.NONE);
		fHWirRation.setDigits(2);
		fHWirRation.setMinimum(0);
		fHWirRation.setMaximum(130);
		fHWirRation.setIncrement(10);
		fHWirRation.setSelection(80);

		addLabel(composite, "Bus: ");
		fHwBus = addLabel(composite, "");

		addLabel(composite, "Package: ");
		fHwPackageType = addLabel(composite, "");

		addLabel(composite, "Rev. Number: ");
		fHwRevnumber = addLabel(composite, "");

		Dialog.applyDialogFont(composite);
		setControl(composite);
		initializeFields();
	}

	/**
	 * Initialize the dialogs fields
	 */
	private void initializeFields() {
		fHWName.setText(fHW.getComponentName());	
		AddressSelectionPin selectedAddress = fHW.gettSelecteAddress();
		if (selectedAddress == null)
			selectedAddress = AddressSelectionPin.FLOATING; //default
		
		fHWAddress.setSelection(new StructuredSelection(selectedAddress));
		fHWirRation.setSelection((int) Math.round(fHW.getIrBroadbandRatio()*10));
		fHwBus.setText(Short.toString(fHW.getBusId()));
		fHwPackageType.setText(fHW.getPackageType().toString());
		fHwRevnumber.setText(Byte.toString(fHW.getRevNumber()));		
	}

	private void setFieldValuesToHW() {
		fHW.setComponentName(fHWName.getText());
		
		IStructuredSelection  selection = (IStructuredSelection) fHWAddress.getSelection();
		AddressSelectionPin SelectedAddress = (AddressSelectionPin) selection.getFirstElement();
		fHW.setSelectedAddress(SelectedAddress);
		fHW.setIrBroadbandRatio(fHWirRation.getSelection()/10.0);
	}	
	
	
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
	public void setSelection(HardwareProperties hw) {
		super.setSelection(hw);
		if (hw instanceof TSL256xProperties)
			fHW = (TSL256xProperties)hw;		
		setTitle("Configure TSL2561");
		setDescription("Use this page to configure your LIGHT-TO-DIGITAL CONVERTER.");
	}

	@Override
	public HardwareProperties getSelection() {
		return fHW;
	}

}
