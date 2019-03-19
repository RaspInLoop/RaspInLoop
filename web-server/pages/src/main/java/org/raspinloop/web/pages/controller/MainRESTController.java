package org.raspinloop.web.pages.controller;

import java.util.List;

import org.raspinloop.server.model.IModel;
import org.raspinloop.web.pages.dao.GraphModelDao;
import org.raspinloop.web.pages.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class MainRESTController {

  @Autowired
  private GraphModelDao graphModelDAO;

  // URL:
  // http://localhost:8080/api/graphs/model/{graphId}
  // http://localhost:8080/api/graphs/model/{graphId}.xml
  // http://localhost:8080/api/graphs/model/{graphId}.json
  @RequestMapping(value = "/api/graphs/model/{graphId}", //
          method = RequestMethod.GET, //
          produces = { MediaType.APPLICATION_JSON_VALUE, //
                  MediaType.APPLICATION_XML_VALUE })
  @ResponseBody
  public IModel getGraphModel(@PathVariable("graphId") Long graphId) {
      return graphModelDAO.getGraphModel(graphId);
  }

  


  // URL:
  // http://localhost:8080/SomeContextPath/graphmodel/{graphId}
  @RequestMapping(value = "/api/graphs/model/{graphId}", //
          method = RequestMethod.DELETE, //
          produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
  @ResponseBody
  public void deleteGraphModel(@PathVariable("graphId") Long id) {

      System.out.println("(Service Side) Deleting model with Id: " + id);

      graphModelDAO.deleteGraphModel(id);
  }

}