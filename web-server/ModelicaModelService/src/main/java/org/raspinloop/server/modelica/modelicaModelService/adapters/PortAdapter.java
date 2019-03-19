package org.raspinloop.server.modelica.modelicaModelService.adapters;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.stream.XMLStreamException;

import org.openmodelica.corba.parser.ParseException;
import org.raspinloop.server.model.IPoint;
import org.raspinloop.server.model.IPort;
import org.raspinloop.server.modelica.annotations.Icon;
import org.raspinloop.server.modelica.annotations.Placement;
import org.raspinloop.server.modelica.annotations.Point;
import org.raspinloop.server.modelica.mdt.core.IModelicaComponent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PortAdapter implements IPort {


	private IModelicaComponent connector;

	public PortAdapter(IModelicaComponent connector) {
		this.connector = connector;
	}
	
	@Override
	public String getDescription() {
		return connector.getElementName();
	}

	@Override
	public IPoint getPosition() {	
		String annotation = connector.getComponentAnnotation();
		StringReader sr = new StringReader(annotation);
		try {
			Placement i = Placement.build(sr);
			Point point = i.getDiagTransformation().getLower();
			return new Position(point.getX(), point.getY());
		} catch (IOException | ParseException | NumberFormatException e) {
			log.error("unable to decode Placement for {}, annotation[{}] :{}",connector.getFullName(), annotation ,e);			
		}
		return new Position(0,0);
	}

	@Override
	public String getId() {
		return connector.getFullName();		
	}

}
