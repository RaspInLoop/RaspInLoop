package org.raspinloop.pi4j.io.i2c;

public abstract class Register implements IRegister {
	
	final private byte address;
	final private String name;

	public Register(String name, byte addree) {
		this.name = name;
		address = addree;
	}
	@Override
	public byte getAddress() {
		return address;
	}

	@Override
	public String getName() {		
		return name;
	}

	@Override
	abstract public byte read();

	@Override
	abstract public void write(byte value);
}	
