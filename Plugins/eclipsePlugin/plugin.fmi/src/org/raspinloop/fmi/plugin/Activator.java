package org.raspinloop.fmi.plugin;

import java.io.IOException;
import java.net.URL;


import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;


/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	private static final Logger LOG = LoggerFactory.getLogger(Activator.class);
	
	// The plug-in ID
	public static final String PLUGIN_ID = "org.raspinloop.fmi"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		configureLogbackInBundle(context.getBundle());
		LOG.info(PLUGIN_ID+ " STARTED!");
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		configureLogbackInBundle(context.getBundle());
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	public void log(String msg) {
	      log(msg, null);
	   }
	
	public void log(String msg, Exception e) {
	      getLog().log(new Status(Status.INFO, PLUGIN_ID, org.eclipse.core.runtime.Status.OK, msg, e));
	      LOG.info(msg, e);
	   }
	
	public void logError(String msg) {
	      logError(msg, null);
	   }
	
	public void logError(String msg, Exception e) {
	      getLog().log(new Status(Status.ERROR, PLUGIN_ID, org.eclipse.core.runtime.Status.OK, msg, e));
	      LOG.info(msg, e);
	   }
	
	private void configureLogbackInBundle(Bundle bundle) throws JoranException, IOException {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        JoranConfigurator jc = new JoranConfigurator();
        jc.setContext(context);
        context.reset();

        // this assumes that the logback.xml file is in the root of the bundle.
        URL logbackConfigFileUrl = FileLocator.find(bundle, new Path("logback.xml"),null);
        jc.doConfigure(logbackConfigFileUrl.openStream());
    }

}
