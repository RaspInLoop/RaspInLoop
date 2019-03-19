package org.raspinloop.server.modelica.modelicaModelService;

import javax.annotation.Resource;

import org.openmodelica.corba.ConnectException;
import org.raspinloop.server.model.IComponent;
import org.raspinloop.server.model.IPackage;
import org.raspinloop.server.modelica.mdt.core.compiler.CompilerInstantiationException;
import org.raspinloop.server.modelica.mdt.core.compiler.InvocationError;
import org.raspinloop.server.modelica.mdt.core.compiler.UnexpectedReplyException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MainRESTController {

	@Resource
	IModelFactory modeLFactory;

	// URL:
	// http://localhost:8080/api/modelica/component/{name}
	// http://localhost:8080/api/modelica/component/{name}.json
	@RequestMapping(value = "/api/modelica/component/{name}", //
			method = RequestMethod.GET, //
			produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public IComponent componentRequest(@PathVariable("name") String name) {
		try {
			return modeLFactory.createComponent(name);
		} catch (ConnectException | UnexpectedReplyException | CompilerInstantiationException | InvocationError e) {
			log.error("cannot create component{}: {}", name, e.getMessage());
		}
		return null;
	}

	// URL:
	// http://localhost:8080/api/modelica/package/{name}
	// http://localhost:8080/api/modelica/package/{name}.json
	@RequestMapping(value = "/api/modelica/package/{name}", //
			method = RequestMethod.GET, //
			produces = { MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public IPackage packageRequest(@PathVariable("name") String name) {
		try {
			return modeLFactory.createPackage(name);
		} catch (ConnectException | UnexpectedReplyException | CompilerInstantiationException | InvocationError e) {
			log.error("cannot create package {}: {}", name, e.getMessage());
		}
		return null;
	}

}