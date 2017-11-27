package org.raspinloop.pi4j.io.i2c;

public abstract class RoRegister extends Register implements IRegister {

	public RoRegister(String name, byte addree) {
		super(name, addree);
	}

	@Override
	abstract public byte read();

	@Override
	public void write(byte value) {		
	}

}
