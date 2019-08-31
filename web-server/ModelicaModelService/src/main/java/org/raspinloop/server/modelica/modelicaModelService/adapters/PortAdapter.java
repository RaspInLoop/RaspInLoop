package org.raspinloop.server.modelica.modelicaModelService.adapters;

import java.io.IOException;
import java.io.StringReader;

import org.openmodelica.corba.ConnectException;
import org.openmodelica.corba.parser.ParseException;
import org.raspinloop.server.model.IPoint;
import org.raspinloop.server.model.IPort;
import org.raspinloop.server.model.ISize;
import org.raspinloop.server.modelica.annotations.Extent;
import org.raspinloop.server.modelica.annotations.Placement;
import org.raspinloop.server.modelica.annotations.Point;
import org.raspinloop.server.modelica.mdt.core.IModelicaComponent;
import org.raspinloop.server.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.raspinloop.server.modelica.mdt.core.compiler.InvocationError;
import org.raspinloop.server.modelica.mdt.core.compiler.UnexpectedReplyException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PortAdapter implements IPort {


	private IModelicaComponent connector;
	private Placement placement = null;

	public PortAdapter(IModelicaComponent connector) {
		this.connector = connector;
		String annotation = connector.getComponentAnnotation();
		log.trace("getting position based on placement : "+annotation);
		StringReader sr = new StringReader(annotation);
		try {
			placement = Placement.build(sr);	
		} catch (IOException | ParseException | NumberFormatException e) {
			log.error("unable to decode Placement for {}, annotation[{}] :{}",connector.getFullName(), annotation ,e);	
		}
	}
	
	@Override
	public String getDescription() {
		String doc = "";
		try {
			 doc = connector.getDocumentation();
		} catch (ConnectException | UnexpectedReplyException | InvocationError | CompilerInstantiationException e) {
			log.warn("no documentation for {}: {}",connector.getFullName(), e.getCause());	
		}
		return connector.getElementName() + " " + doc;
	}

	@Override
	public IPoint getPosition() {	
		if (placement == null){
			return new Position(0,0);
		}
		// Translate and scale to map to joinJS coordinate system
		Point newpos = mapToJoinS(placement);
		return new Position(newpos.getX(), newpos.getY());		
	}

	//Ideally, it should be base of extends of the icon/diag modelica class. 	
	private Point mapToJoinS(Placement placement) {
		Point point = placement.getIconTransformation().getOrigin();
		double x = point.getX();
		double y = point.getY();
		
		Extent ext = placement.getIconTransformation().getExtent();
		x=x + ext.getCenter().getX(); 
		y=y -  ext.getCenter().getY();
				
		// Coordinate system is first quadrant for Modelica
		// while is second quadrant (Y pointing to the bottom)		
		return new Point(x, -y);
	}

	public double getOrientation(){
		if (placement == null){
			return 0.0;
		}
		return placement.getIconTransformation().getRotation();
	}
	
	public ISize getsize(){
		if (placement == null){
			return new Size(5,5);
		}
		//TODO: should be scaled relatively to icon
		return new Size(placement.getIconTransformation().getExtent().getWidth(), placement.getIconTransformation().getExtent().getHeight());		
	}
	
	@Override
	public String getId() {
		return connector.getFullName();		
	}

}
