/*******************************************************************************
 * Copyright 2018 RaspInLoop
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
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
