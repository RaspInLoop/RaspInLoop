package org.raspinloop.modelica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.modelica.mdt.core.IModelicaClass;
import org.modelica.mdt.core.IModelicaComponent;
import org.modelica.mdt.core.IStandardLibrary;
import org.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.modelica.mdt.core.compiler.InvocationError;
import org.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.openmodelica.corba.ConnectException;
import org.raspinloop.web.pages.model.IComponent;
import org.raspinloop.web.pages.model.IParameter;
import org.raspinloop.web.pages.model.IPoint;
import org.raspinloop.web.pages.model.IPortGroup;
import org.raspinloop.web.pages.model.ISize;
import org.raspinloop.web.pages.model.mock.Point;
import org.raspinloop.web.pages.model.mock.Size;

public class ComponentAdapter implements IComponent {

	private IModelicaClass moClass;
	private String documentation;
	
	private List<IModelicaComponent> connectors = new ArrayList<>();
	private IStandardLibrary std;

	public ComponentAdapter(IStandardLibrary std, IModelicaClass moClass)
			throws ConnectException, UnexpectedReplyException, InvocationError, CompilerInstantiationException {
		this.std = std;
		this.moClass = moClass;
		this.documentation = moClass.getDocumentation();		
		buildConnectors();

	}

	private void buildConnectors() {
		addConnectors(moClass);		
	}	
	
	private void addConnectors(IModelicaClass moclass){		
		try {
			moClass.getInheritedClasses().forEach(this::addConnectors);
			moclass.getChildren().stream().filter(e -> e instanceof IModelicaComponent )
			.map(e -> (IModelicaComponent) e)				
			.filter(IModelicaComponent::isConnector)
			.forEach(connectors::add);
		} catch (ConnectException | UnexpectedReplyException | InvocationError | CompilerInstantiationException e) {
			//TODO Logger
		}
	}


	@Override
	public String getId() {
		return moClass.getFullName();
	}

	@Override
	public String getName() {
		return moClass.getElementName();
	}

	@Override
	public String getDescription() {
		return documentation;
	}

	@Override
	public String getHtmlDocumentation() {
		return "";
	}

	@Override
	public String getSvgContent() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public IPoint getPosition() {
		Random rand = new Random();
		
		int x = rand.nextInt(500) + 10;
		int y = rand.nextInt(500) + 10;
		return new Point(x, y);
	}

	@Override
	public ISize getSize() {
		return new Size(100, 100);
	}

	@Override
	public Set<IPortGroup> getPortGroups() {		
		return PortGroupAdapter.createSet(std, connectors);
	}

	@Override
	public Set<IParameter> getParameters() {
		//TODO
		return Collections.emptySet();
	}

}
