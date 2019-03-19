package org.raspinloop.server.modelica.modelicaModelService.adapters.svg;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public interface StyleBuilder {

	void build(XMLStreamWriter writer) throws XMLStreamException;

}
