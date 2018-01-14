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
