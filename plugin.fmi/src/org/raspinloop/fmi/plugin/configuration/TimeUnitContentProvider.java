package org.raspinloop.fmi.plugin.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class TimeUnitContentProvider implements IStructuredContentProvider {

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
			
			 ArrayList<TimeUnit> arrayList = new ArrayList<TimeUnit>((Collection<? extends TimeUnit>)inputElement);			
			return arrayList.toArray();
		}
		else if ( inputElement instanceof TimeUnit[])
			return (Object[]) inputElement;
		else
			return new TimeUnit[0];
	}

}