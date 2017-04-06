package org.raspinloop.fmi.internal.fmu;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.raspinloop.fmi.hwemulation.GpioProviderHwEmulation;
import org.raspinloop.fmi.hwemulation.HwEmulation;
import org.raspinloop.fmi.modeldescription.Constants;
import org.raspinloop.fmi.modeldescription.Fmi2ScalarVariable;
import org.raspinloop.fmi.modeldescription.Fmi2VariableDependency;
import org.raspinloop.fmi.modeldescription.FmiModelDescription;
import org.raspinloop.fmi.modeldescription.FmiModelDescription.LogCategories.Category;

public class FMU {

	public interface Locator {
		URL resolve(URL url);
	}

	final static Logger logger = Logger.getLogger(FMU.class);

	public static void generate(File fmuFileName, HwEmulation ch, Locator locator) throws FMUGenerateException {
		try (ZipOutputStream fmuzip = new ZipOutputStream(new FileOutputStream(fmuFileName))) {
			FMU fmu = new FMU();

			byte[] modelDescription = null;

			modelDescription = fmu.generateDescription(ch);

			fmuzip.putNextEntry(new ZipEntry("modelDescription.xml"));
			copy(modelDescription, fmuzip);
			fmuzip.closeEntry();

			fmuzip.putNextEntry(new ZipEntry("model.png"));
			copy(fmu.getClass().getResourceAsStream("model.png"), fmuzip);
			fmuzip.closeEntry();

			fmuzip.putNextEntry(new ZipEntry("sources/"));
			fmuzip.closeEntry();

			fmuzip.putNextEntry(new ZipEntry("documentation/"));
			fmuzip.closeEntry();

			// put coSimulation so /dll

//			fmuzip.putNextEntry(new ZipEntry("binaries/win32/ril_fmi.dll"));
//			copy(fmu.getClass().getResourceAsStream("binaries/win32/ril_fmi.dll"), fmuzip);
//			fmuzip.closeEntry();

			fmuzip.putNextEntry(new ZipEntry("binaries/win64/ril_fmi.dll"));
			copy(fmu.getClass().getResourceAsStream("binaries/win64/ril_fmi.dll"), fmuzip);
			fmuzip.closeEntry();

			fmuzip.putNextEntry(new ZipEntry("binaries/linux32/ril_fmi.so"));
			copy(fmu.getClass().getResourceAsStream("binaries/linux32/ril_fmi.so"), fmuzip);
			fmuzip.closeEntry();

			fmuzip.putNextEntry(new ZipEntry("binaries/linux64/ril_fmi.so"));
			copy(fmu.getClass().getResourceAsStream("binaries/linux64/ril_fmi.so"), fmuzip);
			fmuzip.closeEntry();

		}

		catch (IOException | JAXBException e) {
			throw new FMUGenerateException("Cannot generate fmu file: " + e.getMessage(), e);
		}

	}

	private static void copy(InputStream in, OutputStream out) throws IOException {
		if (in == null)
			return;
		byte[] buffer = new byte[1024];
		while (true) {
			int readCount = in.read(buffer);
			if (readCount < 0) {
				break;
			}
			out.write(buffer, 0, readCount);
		}
	}

	private static void copy(byte[] bytes, OutputStream out) throws IOException {
		out.write(bytes, 0, bytes.length);
		out.flush();
	}

	private byte[] generateDescription(HwEmulation ch) throws IOException, JAXBException {

		FmiModelDescription model = new FmiModelDescription();
		model.setAuthor(Constants.author);
		model.setCopyright(Constants.copyright);
		// TODO model.setDefaultExperiment(DefaultExperiment)
		// TODO model.setDescription(String)
		model.setFmiVersion(Constants.version);
		try {
			model.setGenerationDateAndTime(getXMLGregorianCalendarNow());
		} catch (DatatypeConfigurationException e) {
			logger.warn("Cannot determine generation date " + e.getLocalizedMessage());
		}
		model.setGenerationTool(Constants.generationTool);
		model.setGuid(ch.getHWGuid());
		model.setLicense(Constants.license);
		FmiModelDescription.LogCategories logs = new FmiModelDescription.LogCategories();
		addLogCategory(logs, "logAll");
		addLogCategory(logs, "logError");
		addLogCategory(logs, "logFmiCall");
		addLogCategory(logs, "logEvent");
		model.setLogCategories(logs);
		model.setModelName(ch.getType());
		model.setNumberOfEventIndicators(1L);

		FmiModelDescription.CoSimulation cosimulation = new FmiModelDescription.CoSimulation();
		cosimulation.setCanHandleVariableCommunicationStepSize(true);
		cosimulation.setModelIdentifier(Constants.coSimulationModelIdentifier);
		model.getModelExchangeAndCoSimulation().add(cosimulation);

		FmiModelDescription.ModelStructure structure = new FmiModelDescription.ModelStructure();
		structure.setOutputs(new Fmi2VariableDependency());
		model.setModelStructure(structure);

		FmiModelDescription.ModelVariables variables = new FmiModelDescription.ModelVariables();

		for (Fmi2ScalarVariable scalarVar : ch.getModelVariables()) {
			variables.getScalarVariable().add(scalarVar);
		}
		model.setModelVariables(variables);

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		JAXBContext jaxbContext = JAXBContext.newInstance(FmiModelDescription.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.marshal(model, os);

		// TODO FmiModelDescription.TypeDefinitions typeDefinition = new
		// FmiModelDescription.TypeDefinitions();
		// TODO model.setTypeDefinitions(typeDefinition);

		// TODO model.setUnitDefinitions(UnitDefinitions)
		// TODO model.setVariableNamingConvention(String)
		// TODO model.setVendorAnnotations(new Fmi2Annotation());
		// TODO model.setVersion("")

		return os.toByteArray();
	}

	private void addLogCategory(FmiModelDescription.LogCategories logs, String name) {
		Category logAll = new Category();
		logAll.setName(name);
		logs.getCategory().add(logAll);
	}

	public XMLGregorianCalendar getXMLGregorianCalendarNow() throws DatatypeConfigurationException {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
		XMLGregorianCalendar now = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
		return now;
	}
}
