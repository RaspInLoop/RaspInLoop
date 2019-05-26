package org.raspinloop.server.modelica.modelicaModelService.adapters;

import java.io.IOException;
import java.io.StringReader;

import org.openmodelica.corba.parser.ParseException;
import org.raspinloop.server.model.IPoint;
import org.raspinloop.server.model.IPort;
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
		log.trace("getting position based on placement : "+annotation);
		StringReader sr = new StringReader(annotation);
		try {
			Placement i = Placement.build(sr);
			Point point;
			if (i.getDiagOrigin() != null && i.getDiagOrigin().isValid() ){
				log.trace("DiagOrigin Got for position");
				point = i.getDiagOrigin();
			} else if (i.getDiagTransformation() != null && i.getDiagTransformation().isValid() ){
				log.trace("DiagTransformation Got for position");
				point = i.getDiagTransformation().getCenter();
			} else {
				point = new Point(0.0,0.0);
				log.debug("Neither Origin nor Transformation found for placment");
			}
			//TODO: scale with i.getDiagTransformation().getHeight()/getWidth()
			
			// Translate and scale to map to joinJS coordinate system
			Point newpos = mapToJoinS(point);
			return new Position(newpos.getX(), newpos.getY());
		} catch (IOException | ParseException | NumberFormatException e) {
			log.error("unable to decode Placement for {}, annotation[{}] :{}",connector.getFullName(), annotation ,e);			
		}
		return new Position(0,0);
	}

	//Ideally, it should be base of extends of the icon/diag modelica class. 	
	private Point mapToJoinS(Point point) {
		double x = point.getX();
		double y = point.getY();
		
		//extends of the icon always set to 
		// coordinateSystem(preserveAspectRatio=true, extent={{-100,-100},{100,100}})
		x=x+100.0; 
		y=y-100.0;
		
		 //in joinJs, we scale down.
		x=x/2.0;
		y=y/2.0;
		
		// Coordinate system is first quadrant for Modelica
		// while is second quadrant (Y pointing to the bottom)		
		return new Point(x, -y);
	}

	//TODO: Missing getOrientation
	
	@Override
	public String getId() {
		return connector.getFullName();		
	}

}
