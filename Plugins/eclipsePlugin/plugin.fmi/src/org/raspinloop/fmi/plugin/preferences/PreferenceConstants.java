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

import org.raspinloop.fmi.plugin.Activator;

/**
 * Constant definitions for plug-in preferences
 */
public class PreferenceConstants {

	private static final String PREFIX = Activator.PLUGIN_ID+".";
	
	public static final String P_PATH = "pathPreference";

	public static final String P_BOOLEAN = "booleanPreference";

	public static final String P_CHOICE = "choicePreference";

	public static final String P_STRING = "stringPreference";
	
	public static final String RIL_PREFERENCE_PAGE= PREFIX + "preference_page_context"; //$NON-NLS-1$
	
	public static final String HARDWARE_EXTENSION_POINT_ID = "org.raspinloop.fmi.Hardware";


}
