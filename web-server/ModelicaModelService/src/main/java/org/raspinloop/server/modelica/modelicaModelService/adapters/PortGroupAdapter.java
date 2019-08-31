package org.raspinloop.server.modelica.modelicaModelService.adapters;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.openmodelica.corba.ConnectException;
import org.raspinloop.server.model.IPort;
import org.raspinloop.server.model.IPortGroup;
import org.raspinloop.server.model.IPortGroupDefinition;
import org.raspinloop.server.modelica.mdt.core.IModelicaClass;
import org.raspinloop.server.modelica.mdt.core.IModelicaComponent;
import org.raspinloop.server.modelica.mdt.core.IModelicaElement;
import org.raspinloop.server.modelica.mdt.core.IModelicaProject;
import org.raspinloop.server.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.raspinloop.server.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.raspinloop.server.modelica.modelicaModelService.adapters.svg.SvgFactory;

public class PortGroupAdapter implements IPortGroup {

	private IModelicaClass moClass;
	private List<IModelicaComponent> connectors;
	private SvgFactory svgFactory;

	public static Set<IPortGroup> createSet(SvgFactory svgFactory, IModelicaProject std, List<IModelicaComponent> connectors) {
		Set<IModelicaClass> uniquConnectorType = connectors.stream()
											.map(c -> c.getTypeName())
											.distinct()
											.map(type -> {
											
													try {
														return std.getRootClasses()
																.stream()
																.map(p -> p.getMoClassLoader().getClass(type))
																.findFirst()
																.get();
													} catch (ConnectException | CompilerInstantiationException | UnexpectedReplyException  e) {
														// TODO Auto-generated catch block
														return null;
													}})
											.filter(Objects::nonNull)											
											.collect(Collectors.toSet());
		 
		 
		
		Set<IPortGroup> portAdapters= uniquConnectorType
										.stream()
										.map(type -> {return new PortGroupAdapter(svgFactory, type, connectors.stream()
																							.filter(con -> con.getTypeName().equals(type.getFullName()))
																							.filter(con -> con.getVisibility()==IModelicaElement.Visibility.PUBLIC)
																							.collect(Collectors.toList()));})
										.collect(Collectors.toSet());
		return portAdapters;
	}
	
	public PortGroupAdapter(SvgFactory svgFactory, IModelicaClass moClass, List<IModelicaComponent> connectors){
		this.svgFactory = svgFactory;
		this.moClass = moClass;
		this.connectors = connectors;
		
	}
	
	@Override
	public Set<IPort> getPorts() {
		return connectors.stream().map(PortAdapter::new).collect(Collectors.toSet());		
	}

	@Override
	public IPortGroupDefinition getDefinition() {		
		return new PortGroupDefinitionAdapter(svgFactory, moClass);
	}
	

}
