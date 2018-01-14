/*******************************************************************************
 * Copyright (C) 2018 RaspInLoop
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.raspinloop.fmi.plugin.launcher;


import java.lang.reflect.Constructor;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.raspinloop.config.HardwareBuilder;
import org.raspinloop.config.HardwareBuilderFactory;
import org.raspinloop.fmi.HwEmulation;
import org.raspinloop.fmi.plugin.preferences.PreferenceConstants;

public class PluggedClassBuilder implements HardwareBuilder {

	private Object properties;
	private HardwareBuilderFactory factory;
	private String propertyClassName;
	private int baseref = 0;

	public PluggedClassBuilder(String name) {
		this.propertyClassName = name;
		
	}

	@Override
	public HardwareBuilder setProperties(Object properties) {
		this.properties = properties;
		return this;
	}

	@Override
	public HardwareBuilder setBuilderFactory(HardwareBuilderFactory factory) {
		this.factory = factory;
		return this;
	}

	@Override
	public Object getProperties() {
		return properties;
	}

	@Override
	public HardwareBuilderFactory getBuilderFactory() {
		return factory;
	}

	@Override
	public HwEmulation build() throws Exception {
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry().getExtensionPoint(PreferenceConstants.HARDWARE_EXTENSION_POINT_ID);
		IConfigurationElement[] infos = extensionPoint.getConfigurationElements();
		HwEmulation hardware = null;
		for (int i = 0; i < infos.length; i++) {
			IConfigurationElement element = infos[i];
			if (propertyClassName.equals(element.getAttribute("configClass"))){ //$NON-NLS-1$
				String simulatorClassName = element.getAttribute("simulatorClass");//$NON-NLS-1$
				String contributorName = element.getDeclaringExtension().getContributor().getName();
				Class<?> javaClass = Platform.getBundle(contributorName).loadClass(simulatorClassName);
				Constructor<?> ctor = javaClass.getDeclaredConstructor(HardwareBuilder.class);
				hardware = (HwEmulation) ctor.newInstance(this);
			}
		}
		return hardware;
	}

	@Override
	public HardwareBuilder setBaseReference(int base) {
		this.baseref = base;
		return this;
	}

	@Override
	public int getBaseReference() {
		return baseref;
	}

}
