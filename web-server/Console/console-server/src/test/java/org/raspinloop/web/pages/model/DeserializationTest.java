package org.raspinloop.web.pages.model;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.raspinloop.server.model.IComponent;
import org.raspinloop.server.model.IParameter;
import org.raspinloop.server.model.IPoint;
import org.raspinloop.server.model.IPort;
import org.raspinloop.server.model.IPortGroup;
import org.raspinloop.server.model.IPortGroupDefinition;
import org.raspinloop.server.model.ISize;
import org.raspinloop.web.pages.jsondeserializer.IComponentMixin;
import org.raspinloop.web.pages.jsondeserializer.IParameterMixin;
import org.raspinloop.web.pages.jsondeserializer.IPointMixin;
import org.raspinloop.web.pages.jsondeserializer.IPortGroupDefinitionMixin;
import org.raspinloop.web.pages.jsondeserializer.IPortGroupMixin;
import org.raspinloop.web.pages.jsondeserializer.IPortMixin;
import org.raspinloop.web.pages.jsondeserializer.ISizeMixin;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DeserializationTest {

	@Test
	public void test() throws JsonParseException, JsonMappingException, IOException {
		 ObjectMapper objectMapper = buildMapper();
		 String s =  "{\"name\":\"UtilitiesPackage\","
		 + "\"id\":\"Modelica.Icons.UtilitiesPackage\","
		 + "\"parameters\":[],"
		 + "\"size\":{\"height\":100.0,"
		 + "			\"width\":100.0"
		 + "		 },"
		 + "\"description\":\"Icon for utility packages\","
		 + "\"position\":{\"x\":250.0,\"y\":250.0},"
		 + "\"portGroups\":[],"
		 + "\"svgIcon\":\"<?xml version=\\\"1.0\\\" ?><g class=\\\"icon\\\"><defs></defs><path transform=\\\"translate(0.14 0.41) rotate(-45)\\\" d=\\\"M -1.5 \\\">\""
		 + "}";
		 objectMapper.readValue(s.getBytes(), IComponent.class);
	}

	private static ObjectMapper buildMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.addMixIn(IComponent.class, IComponentMixin.class);
        objectMapper.addMixIn(ISize.class, ISizeMixin.class);
        objectMapper.addMixIn(IParameter.class, IParameterMixin.class);
        objectMapper.addMixIn(IPoint.class, IPointMixin.class);
        objectMapper.addMixIn(IPort.class, IPortMixin.class);
        objectMapper.addMixIn(IPortGroup.class, IPortGroupMixin.class);
        objectMapper.addMixIn(IPortGroupDefinition.class, IPortGroupDefinitionMixin.class);
        objectMapper.setVisibility(objectMapper.getSerializationConfig()
                .getDefaultVisibilityChecker()
                .withFieldVisibility(Visibility.ANY)
                .withGetterVisibility(Visibility.NONE)
                .withSetterVisibility(Visibility.NONE)
                .withCreatorVisibility(Visibility.NONE));
        return objectMapper;
    }
}
