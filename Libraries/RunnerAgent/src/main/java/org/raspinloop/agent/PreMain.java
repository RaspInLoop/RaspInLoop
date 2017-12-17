package org.raspinloop.agent;

import java.lang.instrument.Instrumentation;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.raspinloop.agent.launcherRunnerIpc.IpcConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PreMain {

	final static Logger logger = LoggerFactory.getLogger(PreMain.class);
	
	public static void premain(String agentArgs, Instrumentation inst) {
		logger.info("starting Raspinloop Runner Agent ");
		if (StringUtils.isBlank(agentArgs)) {
			System.err.println("missing jsonFileName");
			System.exit(1);
		}
		logger.debug("org.raspinloop.agent.PreMain " + agentArgs);

		String hdDescriptionJsonFilename = agentArgs;

		try {
			byte[] encoded = Files.readAllBytes(Paths.get(hdDescriptionJsonFilename));
			String jsonConfig = new String(encoded, "UTF-8");

			Handler handler = Handler.build(jsonConfig);
			handler.start(new IpcConnector());
			while (!handler.isReadyForMain()) {
				Thread.sleep(10);
			}
			logger.info("Setup Experiment Done: starting Application");
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}
		// lets execution next premain (Aspect Weaving for replacing hardware and time specific function)
	}
}
