package org.raspinloop.server.modelica.modelicaModelService.adapters;

import org.raspinloop.server.model.IPoint;

import lombok.Value;

@Value
public class Position implements IPoint {

	private double x;
	private double y;

	static IPoint buildDefault() 
	{
		return new Position(250,250);
	}
}
