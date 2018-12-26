package org.raspinloop.modelica;

import java.util.Random;

import org.modelica.mdt.core.IModelicaComponent;
import org.raspinloop.web.pages.model.IPoint;
import org.raspinloop.web.pages.model.IPort;
import org.raspinloop.web.pages.model.mock.Point;

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
		Random rand = new Random();
		int y = rand.nextInt(100) +1 ;
		int x = rand.nextBoolean()?0:100;
		return new Point(x,y);
	}

	@Override
	public String getId() {
		return connector.getFullName();		
	}

}
