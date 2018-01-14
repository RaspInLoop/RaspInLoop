/*******************************************************************************
 * Copyright (C) 2018 RaspInLoop
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.raspinloop.fmi.plugin;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;


/**
 * Bundle of most images used by the Java debug plug-in.
 */
public class Images {


    private static String ICONS_PATH = "/icons/"; //$NON-NLS-1$
	
	// The plugin registry
	private static ImageRegistry fgImageRegistry = null;

	public static final String IMG_HARDWARE= "IMG_HARDWARE";			//$NON-NLS-1$

	
	/**
	 * Returns the image managed under the given key in this registry.
	 * 
	 * @param key the image's key
	 * @return the image managed under the given key
	 */ 
	public static Image get(String key) {
		return getImageRegistry().get(key);
	}
	
	/**
	 * Returns the <code>ImageDescriptor</code> identified by the given key,
	 * or <code>null</code> if it does not exist.
	 */
	public static ImageDescriptor getImageDescriptor(String key) {
		return getImageRegistry().getDescriptor(key);
	}	
	
	/*
	 * Helper method to access the image registry from the JDIDebugUIPlugin class.
	 */
	/* package */ static ImageRegistry getImageRegistry() {
		if (fgImageRegistry == null) {
			initializeImageRegistry();
		}
		return fgImageRegistry;
	}
	
	private static void initializeImageRegistry() {
		fgImageRegistry= new ImageRegistry(getStandardDisplay());
		declareImages();
	}
	
	private static Display getStandardDisplay() {
		Display display;
		display= Display.getCurrent();
		if (display == null)
			display= Display.getDefault();
		return display;		
	}
	
	private static void declareImages() {
		
		declareRegistryImage(IMG_HARDWARE, ICONS_PATH + "hardware.png"); //$NON-NLS-1$
		}

	/**
     * Declare an Image in the registry table.
     * @param key   The key to use when registering the image
     * @param path  The path where the image can be found. This path is relative to where
     *              this plugin class is found (i.e. typically the packages directory)
     */
    private final static void declareRegistryImage(String key, String path) {
        ImageDescriptor desc = ImageDescriptor.getMissingImageDescriptor();
        Bundle bundle = Platform.getBundle("org.raspinloop.fmi.plugin.fmi");
        URL url = null;
        if (bundle != null){
            url = FileLocator.find(bundle, new Path(path), null);
            if(url != null) {
            	desc = ImageDescriptor.createFromURL(url);
            }
        }
        fgImageRegistry.put(key, desc);
    }
}
