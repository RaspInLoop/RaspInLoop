package org.raspinloop.fmi.testtools;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.raspinloop.config.BoardHardware;
import org.raspinloop.config.GsonProperties;
import org.raspinloop.hwemulation.HardwareClassFactory;

public class RaspInLoopHardwareTestRunner extends BlockJUnit4ClassRunner {

	public RaspInLoopHardwareTestRunner(Class<?> klass) throws InitializationError {
		super(klass);		
	}

	 @Override
	  protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
		 HarwareSimulatedTest annotation = method.getAnnotation(HarwareSimulatedTest.class);
		 if ( annotation != null) {
			 try {
				 Path path;
				 URL url = method.getMethod().getDeclaringClass().getResource(annotation.hwPropertiesFile());
				 if (url != null)
					 path = Paths.get(url.toURI());
				 else
					 path = Paths.get(annotation.hwPropertiesFile());
				configureBuilder(path);
			} catch (IOException | URISyntaxException e) {
				 Description description = describeChild(method);
				 notifier.fireTestFailure(new Failure(description, e));
			}
		 } 
		 super.runChild(method, notifier);
	 }

	private void configureBuilder(Path hwPropertiesFile) throws IOException {
		byte[] encoded = Files.readAllBytes(hwPropertiesFile);
		String jsonProps = new String(encoded, "UTF-8");
		GsonProperties conf = new GsonProperties(HardwareClassFactory.INSTANCE());		
		BoardHardware hardwareProperties = conf.read(jsonProps);
		Builder.setConfig(hardwareProperties); 
	}
}
