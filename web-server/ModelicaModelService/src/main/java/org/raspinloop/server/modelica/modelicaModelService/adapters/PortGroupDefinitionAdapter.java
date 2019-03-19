package org.raspinloop.server.modelica.modelicaModelService.adapters;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.stream.XMLStreamException;

import org.openmodelica.corba.parser.ParseException;
import org.raspinloop.server.model.IPortGroupDefinition;
import org.raspinloop.server.modelica.annotations.Icon;
import org.raspinloop.server.modelica.mdt.core.IModelicaClass;
import org.raspinloop.server.modelica.modelicaModelService.adapters.svg.SvgFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PortGroupDefinitionAdapter implements IPortGroupDefinition {

	private IModelicaClass moClass;
	private SvgFactory svgFactory;

	public PortGroupDefinitionAdapter(SvgFactory svgFactory, IModelicaClass moClass) {
		this.svgFactory = svgFactory;
		this.moClass = moClass;
	}

	@Override
	public String getName() {
		return moClass.getElementName();
	}

	@Override
	public String getSvg() {
		String annotation = moClass.getIconAnnotation();
		StringReader sr = new StringReader(annotation);
		try {
			Icon i = Icon.build(sr);
			return svgFactory.build(i, "port-body"); 
		} catch (IOException | ParseException | XMLStreamException e) {
			log.error("unable to decode icon for {}, annotation[{}] :{}",moClass.getFullName(), annotation ,e);
			return "";
		}
	}

}
