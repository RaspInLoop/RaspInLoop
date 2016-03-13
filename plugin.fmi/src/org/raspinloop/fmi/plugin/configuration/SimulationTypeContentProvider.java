package org.raspinloop.fmi.plugin.configuration;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class SimulationTypeContentProvider implements IStructuredContentProvider {

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
			
			 ArrayList<SimulationType> arrayList = new ArrayList<SimulationType>((Collection<? extends SimulationType>)inputElement);			
			return arrayList.toArray();
		}
		else if ( inputElement instanceof SimulationType[])
			return (Object[]) inputElement;
		else
			return new SimulationType[0];
	}

}
