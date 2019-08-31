package org.raspinloop.server.modelica.modelicaModelService.adapters;

import org.raspinloop.server.model.ISize;

import lombok.Value;

@Value
public class Size implements ISize {

	private double height;
	private double width;
	static ISize buildDefault() 
	{
		return new Size(50,50);
	}
}
