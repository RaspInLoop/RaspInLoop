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
