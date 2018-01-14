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

import org.raspinloop.config.HardwareBuilder;
import org.raspinloop.config.HardwareBuilderFactory;
import org.raspinloop.config.HardwareProperties;

public class PluggedClassBuilderFactory implements HardwareBuilderFactory {

	@Override
	public HardwareBuilder createBuilder(HardwareProperties hwProps) {
		HardwareBuilder builder = new PluggedClassBuilder(hwProps.getClass().getName());
		builder.setBuilderFactory(this);
		builder.setProperties(hwProps);
		return builder;
	}

}
