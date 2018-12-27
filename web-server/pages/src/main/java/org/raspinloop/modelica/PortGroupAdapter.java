package org.raspinloop.modelica;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelica.mdt.core.IModelicaClass;
import org.modelica.mdt.core.IModelicaComponent;
import org.modelica.mdt.core.IModelicaProject;
import org.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.openmodelica.corba.ConnectException;
import org.raspinloop.web.pages.model.IPort;
import org.raspinloop.web.pages.model.IPortGroup;
import org.raspinloop.web.pages.model.IPortGroupDefinition;

public class PortGroupAdapter implements IPortGroup {

	private IModelicaClass moClass;
	private List<IModelicaComponent> connectors;

	public static Set<IPortGroup> createSet(IModelicaProject std, List<IModelicaComponent> connectors) {
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
										.map(type -> {return new PortGroupAdapter(type, connectors.stream()
																							.filter(con -> con.getTypeName().equals(type.getFullName()))
																							.collect(Collectors.toList()));})
										.collect(Collectors.toSet());
		return portAdapters;
	}
	
	public PortGroupAdapter(IModelicaClass moClass, List<IModelicaComponent> connectors){
		this.moClass = moClass;
		this.connectors = connectors;
		
	}
	
	@Override
	public Set<IPort> getPorts() {
		return connectors.stream().map(PortAdapter::new).collect(Collectors.toSet());		
	}

	@Override
	public IPortGroupDefinition getDefinition() {		
		return new PortGroupDefinitionAdapter(moClass);
	}
	

}
