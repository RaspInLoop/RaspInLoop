package org.raspinloop.server.modelica.modelicaModelService.adapters;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.xml.stream.XMLStreamException;

import org.openmodelica.corba.ConnectException;
import org.openmodelica.corba.parser.ParseException;
import org.raspinloop.server.model.IComponent;
import org.raspinloop.server.model.IParameter;
import org.raspinloop.server.model.IPoint;
import org.raspinloop.server.model.IPortGroup;
import org.raspinloop.server.model.ISize;
import org.raspinloop.server.modelica.annotations.Icon;
import org.raspinloop.server.modelica.mdt.core.IModelicaClass;
import org.raspinloop.server.modelica.mdt.core.IModelicaComponent;
import org.raspinloop.server.modelica.mdt.core.IModelicaProject;
import org.raspinloop.server.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.raspinloop.server.modelica.mdt.core.compiler.InvocationError;
import org.raspinloop.server.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.raspinloop.server.modelica.modelicaModelService.adapters.svg.SvgFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ComponentAdapter implements IComponent {

	private IModelicaClass moClass;
	private String documentation;
	
	private List<IModelicaComponent> connectors = new ArrayList<>();
	private IModelicaProject std;
	private SvgFactory svgFactory;

	public ComponentAdapter(SvgFactory svgFactory, IModelicaProject std2, IModelicaClass moClass)
			throws ConnectException, UnexpectedReplyException, InvocationError, CompilerInstantiationException {
		this.svgFactory = svgFactory;
		this.std = std2;
		this.moClass = moClass;
		this.documentation = moClass.getDocumentation();		
		buildConnectors();

	}

	private void buildConnectors() {
		addConnectors(moClass);		
	}	
	
	private void addConnectors(IModelicaClass moclass){		
		try {
			moclass.getInheritedClasses().forEach(this::addConnectors);
			moclass.getChildren().stream().filter(e -> e instanceof IModelicaComponent )
			.map(e -> (IModelicaComponent) e)				
			.filter(IModelicaComponent::isConnector)
			.forEach(connectors::add);
		} catch (ConnectException | UnexpectedReplyException | InvocationError | CompilerInstantiationException e) {
			log.error("Cannot Add connector for {}: {}", moclass.getFullName(), e.getMessage());
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
	public String getSvgIcon() {
		List<Icon> icons = new ArrayList<>();
		addClassIconContent(icons, moClass);		
		try {
			return svgFactory.build(icons, "icon");
		} catch (IOException | XMLStreamException e) {
			log.error("unable to converts icon for {}, to svg :{}", moClass.getFullName(), e.getMessage());
			return "";
		}		
	}
	
	private void addClassIconContent(List<Icon> icons, IModelicaClass inheritedClass) {
		String annotation = "";
		try {
			inheritedClass.getInheritedClasses().forEach(c -> addClassIconContent(icons, c));
			annotation = inheritedClass.getIconAnnotation();
			StringReader sr = new StringReader(annotation);
			icons.add(Icon.build(sr));			
		} catch (ConnectException | UnexpectedReplyException | InvocationError | CompilerInstantiationException | IOException | ParseException e) {
			log.error("unable to decode icon for {}, annotation[{}] :{}", inheritedClass.getFullName(), annotation, e.getMessage());
		}
	}
	
//	private void addClassDiagramContent(List<Icon> icons, IModelicaClass inheritedClass) {
//		String annotation = "";
//		try {
//			inheritedClass.getInheritedClasses().forEach(c -> addClassDiagramContent(icons, c));
//			annotation = inheritedClass.getDiagramAnnotation();
//			StringReader sr = new StringReader(annotation);
//			icons.add(Icon.build(sr));			
//		} catch (ConnectException | UnexpectedReplyException | InvocationError | CompilerInstantiationException | IOException | ParseException e) {
//			log.error("unable to decode diagram for {}, annotation[{}] :{}", inheritedClass.getFullName(), annotation, e.getMessage());
//		}
//	}
	
//	@Override
//	public String getSvgContent() {
//		List<Icon> icons = new ArrayList<>();
//		addClassDiagramContent(icons, moClass);		
//		try {
//			return svgFactory.build(icons, "diagram");
//		} catch (IOException | XMLStreamException e) {
//			log.error("unable to converts diagram for {}, to svg :{}", moClass.getFullName(), e.getMessage());
//			return "";
//		}
//	}

	@Override
	public IPoint getPosition() {
		return Position.buildDefault();
	}

	@Override
	public ISize getSize() {
		return  Size.buildDefault();
	}

	@Override
	public Set<IPortGroup> getPortGroups() {		
		return PortGroupAdapter.createSet(svgFactory, std, connectors);
	}

	@Override
	public Set<IParameter> getParameters() {
		//TODO
		return Collections.emptySet();
	}

}
