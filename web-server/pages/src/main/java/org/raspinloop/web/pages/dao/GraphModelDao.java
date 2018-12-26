package org.raspinloop.web.pages.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.modelica.mdt.core.compiler.InvocationError;
import org.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.openmodelica.corba.ConnectException;
import org.raspinloop.modelica.ModelAdapter;
import org.raspinloop.web.pages.model.IModel;
import org.raspinloop.web.pages.model.IModelFactory;
import org.raspinloop.web.pages.model.IParameter;
import org.raspinloop.web.pages.model.IPort;
import org.raspinloop.web.pages.model.IPortGroupDefinition;
import org.raspinloop.web.pages.model.mock.Component;
import org.raspinloop.web.pages.model.mock.Model;
import org.raspinloop.web.pages.model.mock.Parameter;
import org.raspinloop.web.pages.model.mock.Point;
import org.raspinloop.web.pages.model.mock.Port;
import org.raspinloop.web.pages.model.mock.PortGroup;
import org.raspinloop.web.pages.model.mock.PortGroupDefinition;
import org.springframework.stereotype.Repository;

@Repository
public class GraphModelDao {

	  
	@Resource
	IModelFactory modelFactory;
	    private static final Map<Long, String> empMap = new HashMap<>();
	  
	    static {
	        try {
				initEmps();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	  
	    private static void initEmps() throws IOException {
	    	
	    	
	    	IPortGroupDefinition realInputPortDef = new PortGroupDefinition("REAL-INPUT", "<path d=\"M -10,-5 0,0 -10,5 z\" fill=\"#d45500\" class=\"port-body\" />");
			
	    	    	
			IPortGroupDefinition rotationalsPortDef = new PortGroupDefinition("1-DIM-ROT", "<circle  r=\"4\" stroke=\"#8ea7aeff\" stroke-width=\"2\" fill=\"#232e31ff\"  class=\"port-body\" />");
			
			
			Set<IPort> rotationalPorts= new HashSet<>();
	    	rotationalPorts.add(new Port("port2", new Point(100, 33), "Flange_a" ));
	    	
	    	Set<IPort> realInputports = new HashSet<>();
	    	realInputports.add(new Port("port1", new Point(0, 50), "Rotation/minute" ));

	
			PortGroup rotationalsPortsGroup = new PortGroup(rotationalPorts, rotationalsPortDef);
			PortGroup realInputPortsGroup = new PortGroup(realInputports, realInputPortDef);
	    	
			Set<IParameter> parameters = Collections.singleton(new Parameter("test", "type", "un param"));
			Component c1 = new Component("abc", "Compo 1", "Le composant 1", "", "", parameters , new HashSet<>(Arrays.asList(realInputPortsGroup, rotationalsPortsGroup)));
			c1.setPosition(new Point(10,10));
			
			
			Set<IPort> rotationalPorts2= new HashSet<>();
			rotationalPorts2.add(new Port("port1", new Point(0, 50), "Flange_a" ));
			rotationalPorts2.add(new Port("port2", new Point(100, 50), "Flange_b" ));
	    	PortGroup rotationalsPortsGroup2 = new PortGroup(rotationalPorts2, rotationalsPortDef);
			Component c2 = new Component("zaeaz", "Compo 2", "Le composant 2", "", "", parameters, new HashSet<>(Arrays.asList(rotationalsPortsGroup2)));
			c2.setPosition(new Point(210,10));
			
			
			Model mod1 = new Model("model1", Arrays.asList(c1,c2), new ArrayList<>());
	   
	        empMap.put(0L, "Modelica.Mechanics.Rotational.Components.OneWayClutch");
	        empMap.put(1L, "Modelica.Thermal.FluidHeatFlow.Sources.VolumeFlow");
	    }
	  
	   	  
	    public IModel getGraphModel(Long id) {	    	
	        String modelName = empMap.get(id);
			try {
				return modelFactory.createModel(modelName);
			} catch (ConnectException | UnexpectedReplyException | CompilerInstantiationException | InvocationError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	    }
	  
	    public void deleteGraphModel(Long empId) {
	        empMap.remove(empId);
	    }
	  
	
}
