package org.raspinloop.hwemulation;

import java.lang.reflect.Constructor;

import org.raspinloop.config.HardwareBuilder;
import org.raspinloop.config.HardwareBuilderFactory;
import org.raspinloop.fmi.HwEmulation;


public class ClassLoaderBuilder implements HardwareBuilder {

	private Object properties = null;
	private String implementationClassName;
	private HardwareBuilderFactory factory;
	private int baseref = 0;

	
	public ClassLoaderBuilder(String implementationClassName) {
		this.implementationClassName = implementationClassName;
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
	public HardwareBuilderFactory getBuilderFactory() {
		return factory;
	}

	@Override
	public Object getProperties() {
		return properties;
	}
	
	@Override
	public HardwareBuilder setBaseReference(int base) {
		this.baseref = base;
		return this;
	}

	@Override
	public int getBaseReference() {
		return this.baseref;
	}

	@Override
	public HwEmulation build() throws Exception {		
		Constructor<?> constructor = Class.forName(implementationClassName).getConstructor(HardwareBuilder.class);
		Object istance = constructor.newInstance(this);	
		if (!(istance instanceof HwEmulation)) { // we cannot go further
				System.err.println("Hardware ClassLoaderBuilder : Not a HwEmulation implementaion type: "
						+ implementationClassName);
				return null;
			}				
		return (HwEmulation)istance;
	}

	

	
	

}
