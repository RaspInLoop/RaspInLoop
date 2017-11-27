package org.raspinloop.fmi;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.GsonProperties;
import org.raspinloop.config.HardwareBuilder;
import org.raspinloop.fmi.FMU.Locator;
import org.raspinloop.hwemulation.ClassLoaderBuilderFactory;
import org.raspinloop.hwemulation.GpioProviderHwEmulation;
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
