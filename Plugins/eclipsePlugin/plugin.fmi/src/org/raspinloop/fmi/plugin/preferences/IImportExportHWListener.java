/*******************************************************************************
 * Copyright (C) 2018 RaspInLoop
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.raspinloop.fmi.plugin.preferences;

import org.raspinloop.config.HardwareProperties;

public interface IImportExportHWListener {
	HardwareProperties importHardware(String serializedHw);
	void exportHardware(HardwareProperties hw);
}
