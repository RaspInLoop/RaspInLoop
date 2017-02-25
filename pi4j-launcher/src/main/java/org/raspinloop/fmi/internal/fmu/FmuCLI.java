package org.raspinloop.fmi.internal.fmu;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.GsonConfig;
import org.raspinloop.fmi.hwemulation.GpioProviderHwEmulation;
import org.raspinloop.fmi.hwemulation.HardwareBuilder;
import org.raspinloop.fmi.hwemulation.HwEmulation;
import org.raspinloop.fmi.internal.fmu.FMU.Locator;
import org.raspinloop.fmi.internal.hwemulation.ClassLoaderBuilderFactory;

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
		GsonConfig deserialiser = new GsonConfig(hcl);
		
		BoardHardware hardwareProperties = deserialiser.read(new String(Files.readAllBytes(Paths.get(configFileName)),"UTF-8"));
		ClassLoaderBuilderFactory clbf = new ClassLoaderBuilderFactory();
		HardwareBuilder builder = clbf.createBuilder(hardwareProperties);
		HwEmulation emulationImplementation = builder.build();
		Locator locator = new FMU.Locator() {
			   public URL resolve(URL url) { return url; }
			};
		FMU.generate(new File(fmuFilename), (GpioProviderHwEmulation)emulationImplementation, locator);
			} catch (Exception e) {
				System.err.println("FmuCLI Exception:"+ e.getMessage());
			}
	}	
	
	private static void printUsage() {		
		System.err.println("java -cp pi4j-launcher org.raspinloop.fmi.fmu.FMU 'fmuFilename.fmu' 'jsonConfigFilename' ");		
	}
}
