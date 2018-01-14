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
package org.raspinloop.fmi;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.GsonProperties;
import org.raspinloop.hwemulation.HardwareClassFactory;

public class FmuCLI {

	public static void main(String[] args) {
		if (args.length != 2){
			printUsage();
			System.exit(1);
		}
			try{
		String fmuFilename = args[0];
		String configFileName = args[1];
		HardwareClassFactory hcl = HardwareClassFactory.INSTANCE();
		GsonProperties deserialiser = new GsonProperties(hcl);
		
		BoardHardware hardwareProperties = deserialiser.read(new String(Files.readAllBytes(Paths.get(configFileName)),"UTF-8"));
		FMU.generate(new File(fmuFilename),hardwareProperties);
			} catch (Exception e) {
				System.err.println("FmuCLI Exception:"+ e.getMessage());
			}
	}	
	
	private static void printUsage() {		
		System.err.println("java -cp Core org.raspinloop.fmi.FmuCLI 'fmuFilename.fmu' 'jsonConfigFilename' ");		
	}
}
