package org.raspinloop.server.modelica.modelicaModelService.adapters;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.xml.stream.XMLStreamException;

import org.openmodelica.corba.ConnectException;
import org.openmodelica.corba.parser.ParseException;
import org.raspinloop.server.model.IPackage;
import org.raspinloop.server.modelica.annotations.Icon;
import org.raspinloop.server.modelica.mdt.core.IModelicaClass;
import org.raspinloop.server.modelica.mdt.core.IModelicaClass.Restriction;
import org.raspinloop.server.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.raspinloop.server.modelica.mdt.core.compiler.InvocationError;
import org.raspinloop.server.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.raspinloop.server.modelica.modelicaModelService.adapters.svg.SvgFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PackageAdapter implements IPackage {

	private IModelicaClass moClass;
	private String documentation;
		
	private SvgFactory svgFactory;

	public PackageAdapter(SvgFactory svgFactory, IModelicaClass moClass)
			throws ConnectException, UnexpectedReplyException, InvocationError, CompilerInstantiationException {
		this.svgFactory = svgFactory;
		this.moClass = moClass;
		this.documentation = moClass.getDocumentation();		
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
		String annotation = moClass.getIconAnnotation();
		StringReader sr = new StringReader(annotation);
		try {
			Icon i = Icon.build(sr);
			return svgFactory.build(i,"icon");
		} catch (IOException | ParseException | XMLStreamException e) {
			log.error("unable to decode icon for {}, annotation[{}] :{}",moClass.getFullName(), annotation ,e);
			return "";
		}
	}
		

	@Override
	public Collection<String> getComponentsName() {
		try {
			return moClass.getChildren().stream()
			.filter(IModelicaClass.class::isInstance)
			.map(IModelicaClass.class::cast)
			.filter(this::isBlockOrModel )
			.map(e -> e.getElementName())
			.collect(Collectors.toList());
		} catch (ConnectException | UnexpectedReplyException | InvocationError | CompilerInstantiationException e) {
			return Collections.emptyList();
		}
	}

	

	@Override
	public Collection<String> getPackagesNames() {
		try {
			return moClass.getChildren().stream()
			.filter(IModelicaClass.class::isInstance)
			.map(IModelicaClass.class::cast)
			.filter(this::isPackage )
			.map(e -> e.getElementName())
			.collect(Collectors.toList());
		} catch (ConnectException | UnexpectedReplyException | InvocationError | CompilerInstantiationException e) {
			return Collections.emptyList();
		}
	}
	
	private boolean isPackage(IModelicaClass modelicaClass) {
		try {
			Restriction restriction = modelicaClass.getRestriction();

			return restriction.equals(Restriction.PACKAGE);
		} catch (ConnectException | CompilerInstantiationException | UnexpectedReplyException | InvocationError e1) {
			return false;
		}
	}

	public boolean isBlockOrModel(IModelicaClass modelicaClass) {
		try {
			Restriction restriction = modelicaClass.getRestriction();

			return restriction.equals(Restriction.BLOCK) || restriction.equals(Restriction.MODEL);
		} catch (ConnectException | CompilerInstantiationException | UnexpectedReplyException | InvocationError e1) {
			return false;
		}
	}



	
}
