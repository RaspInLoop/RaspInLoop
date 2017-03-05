package org.raspinloop.fmi.hwemulation;

/**
 * Builders are required to build Hardware classes based on their properties.
 * They contain properties required by the created Hardware class
 * @author Motte
 *
 */
public interface HardwareBuilder {
	HardwareBuilder setProperties(Object properties);
	
	/**
	 * Register component in simulated board. baseref is provided to offset all internal ref. 
	 * 
	 * @param baseref: the offset to apply to all variable references
	 * @return the number of var used in this component.
	 */	
	HardwareBuilder setBaseReference(int base);
	
	
	HardwareBuilder setBuilderFactory(HardwareBuilderFactory factory);
	Object getProperties();
	HardwareBuilderFactory getBuilderFactory();
	int getBaseReference();
	HwEmulation build() throws Exception;
}
