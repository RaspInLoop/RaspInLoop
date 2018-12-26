package org.raspinloop.web.pages.model.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Documentation {
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Integer id;
		
		private String documentation;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getDocumentation() {
			return documentation;
		}

		public void setDocumentation(String documentation) {
			this.documentation = documentation;
		}	
}
