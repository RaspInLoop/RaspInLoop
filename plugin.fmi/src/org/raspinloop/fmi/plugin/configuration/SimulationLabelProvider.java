package org.raspinloop.fmi.plugin.configuration;

import org.eclipse.jface.viewers.LabelProvider;

public class SimulationLabelProvider extends LabelProvider{

	@Override
	public String getText(Object element) {
		if (element instanceof SimulationType)
			switch((SimulationType)element) {
				case FMU: return "Fonctional Mockup Unit";
				case STAND_ALONE: return "Stand Alone";
				default: return "";		
			}
		else
			return super.getText(element);
	}
}
 