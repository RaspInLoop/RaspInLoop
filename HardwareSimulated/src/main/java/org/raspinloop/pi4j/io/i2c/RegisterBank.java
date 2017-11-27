package org.raspinloop.pi4j.io.i2c;

import java.util.HashMap;
import java.util.Map;

public class RegisterBank {

	private Map<Byte, IRegister> registers = new HashMap<>();
	
	RegisterBank() {		
	}

	public RegisterBank add(IRegister register) {
		registers.put(register.getAddress(), register);
		return this;
	}
	
	public byte read (byte regAddress){
		IRegister reg = registers.get(regAddress);
		if (reg != null)
			return reg.read();
		else
			return 0;
	}
	
	public void write (byte regAddress, byte value){
		IRegister reg = registers.get(regAddress);
		if (reg != null)
			reg.write(value);
	}
	
}