package org.raspinloop.fmi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

public class Boot {

	final static Logger logger = Logger.getLogger(Boot.class);

	public static void main(String[] args) throws JAXBException {
		logger.info("starting pi4j-launcher "+Boot.class.getPackage().getImplementationVersion());
		logger.debug("org.raspinloop.fmi.Boot "+ Arrays.toString(args));
		String hdDescriptionJsonFilename = args[0];
		String mainclassName = args[1];
		int from;
		int to;
		if (args.length  > 2){
			from = 2;
			to = args.length-1;
		} else {
			from = 0;
			to = 0;
		}
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(hdDescriptionJsonFilename));
			String jsonConfig = new String(encoded, "UTF-8");

			final String[] otherArgs = Arrays.copyOfRange(args, from, to, String[].class);
			Handler.build(jsonConfig, mainclassName, otherArgs).start(new Server());

		} catch (ClassNotFoundException | IOException e) {
			logger.fatal(e);
		}
	}		
}
