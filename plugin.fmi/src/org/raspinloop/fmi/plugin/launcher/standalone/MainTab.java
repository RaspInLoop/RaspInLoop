package org.raspinloop.fmi.plugin.launcher.standalone;

import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.internal.ui.SWTFactory;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.raspinloop.fmi.plugin.Activator;
import org.raspinloop.fmi.plugin.configuration.SimulationType;
import org.raspinloop.fmi.plugin.configuration.TimeUnitContentProvider;
import org.raspinloop.fmi.plugin.configuration.TimeUnitLabelProvider;
import org.raspinloop.fmi.plugin.launcher.RilMainTab;

public class MainTab extends RilMainTab {

	private static final TimeUnit[] AVAILABLE_TIME_UNIT = new TimeUnit[]{TimeUnit.MICROSECONDS, TimeUnit.MILLISECONDS, TimeUnit.SECONDS, TimeUnit.MINUTES, TimeUnit.HOURS, TimeUnit.DAYS};

	protected Spinner fStandAloneTimeIncrement;
	protected Spinner fStandAloneTimeRatio;
	protected Spinner fStandAloneEndTime;
	protected Label lStandAloneTimeIncrement;
	protected Label lStandAloneTimeRatio;
	protected Label lStandAloneEndTime;
	protected ComboViewer fStandAloneTimeIncrementCombo;
	protected ComboViewer fStandAloneEndTimeCombo;
	
	@Override
	protected void displaySimulationType() {

		fStandAloneTimeIncrement.setVisible(true);
		lStandAloneTimeIncrement.setVisible(true);
		fStandAloneTimeIncrementCombo.getCombo().setVisible(true);
		fStandAloneTimeRatio.setVisible(true);
		lStandAloneTimeRatio.setVisible(true);
		fStandAloneEndTime.setVisible(true);
		lStandAloneEndTime.setVisible(true);
		fStandAloneEndTimeCombo.getCombo().setVisible(true);

	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy config) {
		super.performApply(config);
		
		config.setAttribute(ATTR_SIMULATION_TYPE, SimulationType.STAND_ALONE.toString());
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

	}
	
	@Override
	public void initializeFrom(ILaunchConfiguration config) {		
		super.initializeFrom(config);
		try {
		String standAloneTimeIncrement = config.getAttribute(ATTR_STANDALONE_TIME_INCREMENT, "1");
		try {
			fStandAloneTimeIncrement.setSelection(Integer.parseInt(standAloneTimeIncrement));
		} catch (NumberFormatException e) {
			fStandAloneTimeIncrement.setSelection(1);
		}

		TimeUnit standAloneTimeIncrementUnit = TimeUnit.valueOf(config.getAttribute(ATTR_STANDALONE_TIME_INCREMENT_UNIT, TimeUnit.SECONDS.toString()));
		final ISelection timeIncrementUnitSelection = new StructuredSelection(standAloneTimeIncrementUnit);
		fStandAloneTimeIncrementCombo.setSelection(timeIncrementUnitSelection);

		String standAloneTimeRatio = config.getAttribute(ATTR_STANDALONE_TIME_RATIO, "1");
		fStandAloneTimeRatio.setSelection(new Double(Double.parseDouble(standAloneTimeRatio) * 1000.0).intValue());

		String standAloneEndTime = config.getAttribute(ATTR_STANDALONE_END_TIME, "60");
		try {
			fStandAloneEndTime.setSelection(Integer.parseInt(standAloneEndTime));
		} catch (NumberFormatException e) {
			fStandAloneEndTime.setSelection(60);
		}

		TimeUnit standAloneEndTimeUnit = TimeUnit.valueOf(config.getAttribute(ATTR_STANDALONE_END_TIME_UNIT, TimeUnit.SECONDS.toString()));
		final ISelection standAloneEndTimeUnitSelection = new StructuredSelection(standAloneEndTimeUnit);
		fStandAloneEndTimeCombo.setSelection(standAloneEndTimeUnitSelection);
		} catch (CoreException e) {
			Activator.getDefault().log("Cannot set stand alone parameters: ", e);
		}
	}

	@Override
	protected void createSimulationTypeEditor(Composite parent) {
		{
			Group group = SWTFactory.createGroup(parent,  "Standalone Simulation Parammeters:",3, 1, GridData.FILL_HORIZONTAL);			
			
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
	}
}
