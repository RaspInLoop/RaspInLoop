package org.raspinloop.fmi.plugin.configuration;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.raspinloop.config.HardwareConfig;

public class HardwareContentProvider implements IStructuredContentProvider {
	
	public HardwareContentProvider() {
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
			
			 ArrayList<HardwareConfig> arrayList = new ArrayList<HardwareConfig>((Collection<? extends HardwareConfig>)inputElement);			
			return arrayList.toArray();
		}
		else if ( inputElement instanceof HardwareConfig[])
			return (Object[]) inputElement;
		else
			return new HardwareConfig[0];
	}
}