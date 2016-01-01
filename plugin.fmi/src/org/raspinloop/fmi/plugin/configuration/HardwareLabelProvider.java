package org.raspinloop.fmi.plugin.configuration;

import org.eclipse.jface.viewers.LabelProvider;
import org.raspinloop.config.HardwareConfig;

public class HardwareLabelProvider extends LabelProvider {
	@Override
	  public String getText(Object element) {
	    if (element instanceof HardwareConfig) {
	    	HardwareConfig hw = (HardwareConfig) element;
	      return hw.getName();
	    }
	    return super.getText(element);
	  }
}