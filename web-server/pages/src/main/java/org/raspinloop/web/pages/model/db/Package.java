package org.raspinloop.web.pages.model.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity // This tells Hibernate to make a table out of this class
public class Package {

	    @Id
	    @GeneratedValue(strategy=GenerationType.AUTO)
	    private Integer id;
	    
	    private String name;
	    
	    @ManyToOne
		private Documentation documentation;
		
		@ManyToOne
		private Icon icon;
		
	    
	    @ManyToOne
	    private Package parentPackage;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Package getPackageId() {
			return parentPackage;
		}

		public void setPackageId(Package parentPackage) {
			this.parentPackage = parentPackage;
		}
	    
	    
}
