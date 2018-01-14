/*******************************************************************************
 * Copyright (C) 2018 RaspInLoop
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.raspinloop.fmi.plugin.configuration;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.raspinloop.config.HardwareProperties;

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
			
			 ArrayList<HardwareProperties> arrayList = new ArrayList<HardwareProperties>((Collection<? extends HardwareProperties>)inputElement);			
			return arrayList.toArray();
		}
		else if ( inputElement instanceof HardwareProperties[])
			return (Object[]) inputElement;
		else
			return new HardwareProperties[0];
	}
}
