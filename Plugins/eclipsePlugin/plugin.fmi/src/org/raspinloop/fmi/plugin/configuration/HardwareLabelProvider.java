package org.raspinloop.fmi.plugin.configuration;

import org.eclipse.jface.viewers.LabelProvider;
import org.raspinloop.config.HardwareProperties;

public class HardwareLabelProvider extends LabelProvider {
	@Override
	  public String getText(Object element) {
	    if (element instanceof HardwareProperties) {
	    	HardwareProperties hw = (HardwareProperties) element;
	      return hw.getComponentName();
	    }
	    return super.getText(element);
	  }
}