package org.raspinloop.web.pages.model.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity // This tells Hibernate to make a table out of this class
public class Model {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String reference;
	
    @ManyToOne
    private Model parentModel;
    
	@ManyToOne
	private Documentation documentation;
	
	@ManyToOne
	private Icon icon; 
	
	@ManyToOne
	private Diagram diagram;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Model getParentModel() {
		return parentModel;
	}

	public void setParentModel(Model parentModel) {
		this.parentModel = parentModel;
	}

	public Documentation getDocumentation() {
		return documentation;
	}

	public void setDocumentation(Documentation documentation) {
		this.documentation = documentation;
	}

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public Diagram getDiagram() {
		return diagram;
	}

	public void setDiagram(Diagram diagram) {
		this.diagram = diagram;
	}
	
	
}
