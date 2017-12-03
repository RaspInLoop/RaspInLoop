package org.raspinloop.fmi.plugin.preferences;

import java.util.List;

import org.raspinloop.config.HardwareProperties;

public interface IAddHardwareDialogRequestor {

	/**
	 * Reply whether or not a new Hardware of the specified name would
	 * constitute a duplicate.
	 * 
	 * @param name the name of a potential new Hardware
	 * @return whether a new Hardware with the specified name would be a duplicate Hardware
	 */
	public boolean isDuplicateName(String name, List<String> names);
	
	/**
	 * Notification that a Hardware has been added from the <code>AddHardwareDialog</code>.
	 * 
	 * @param Hardware the added Hardware
	 */
	public void hwAdded(HardwareProperties hardware); 
}
