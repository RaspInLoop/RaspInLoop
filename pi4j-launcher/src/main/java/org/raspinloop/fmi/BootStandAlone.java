package org.raspinloop.fmi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.raspinloop.fmi.modeldescription.Fmi2ScalarVariable;
import org.raspinloop.standalone.TimeSequencer;

public class BootStandAlone {

	final static Logger logger = Logger.getLogger(BootStandAlone.class);

	public static void main(String[] args) throws JAXBException {
		logger.info("starting pi4j-launcher "+BootStandAlone.class.getPackage().getImplementationVersion());
		logger.debug("org.raspinloop.fmi.Boot "+ Arrays.toString(args));
		//TODO use command line parser to have more flexibility
		String hdDescriptionJsonFilename = args[0];
		String guid = args[1];
		double stepIncrement = Double.parseDouble(args[2]);
		double duration = Double.parseDouble(args[3]);
		double timeRatio = Double.parseDouble(args[4]);
		
		String mainclassName = args[5];
		

		int from;
		int to;
		if (args.length > 6) {
			from = 6;
			to = args.length - 1;
		} else {
			from = 0;
			to = 0;
		}
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(hdDescriptionJsonFilename));
			String jsonConfig = new String(encoded, "UTF-8");

			final String[] otherArgs = Arrays.copyOfRange(args, from, to, String[].class);
			List<Fmi2ScalarVariable> varList;
			Handler.build(jsonConfig, mainclassName, otherArgs).start(new TimeSequencer(guid, stepIncrement, timeRatio, duration));

		} catch (ClassNotFoundException | IOException e) {
			logger.fatal(e);
		}
	}
}
