package org.raspinloop.config;


/**
 * Implementation of this interface will be used to create Builders.<p>
 *
 * Builders are required to build Hardware classes based on their properties. Several kind of builders
 * can be created following the policy of class loading. (standard, from osgi bundle, ...)
 * 
 * 
 * @author Motte
 *
 */
public interface  HardwareBuilderFactory {
	HardwareBuilder createBuilder(HardwareProperties hwProps);
}
