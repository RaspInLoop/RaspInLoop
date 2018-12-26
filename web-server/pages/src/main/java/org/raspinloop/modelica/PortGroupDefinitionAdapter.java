package org.raspinloop.modelica;

import org.modelica.mdt.core.IModelicaClass;
import org.raspinloop.web.pages.model.IPortGroupDefinition;

public class PortGroupDefinitionAdapter implements IPortGroupDefinition {

	private IModelicaClass moClass;

	public PortGroupDefinitionAdapter(IModelicaClass moClass) {
		this.moClass = moClass;
	}

	@Override
	public String getName() {
		return moClass.getElementName();
	}

	@Override
	public String getSvg() {
		// TODO Auto-generated method stub
		return "<path d=\"M -10,-5 0,0 -10,5 z\" fill=\"#d45500\" class=\"port-body\" />";
	}

}
