package org.raspinloop.web.pages;

import org.raspinloop.modelica.ModelicaModelFactory;
import org.raspinloop.web.pages.model.IModelFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PagesApplication {

	public static void main(String[] args) {
		SpringApplication.run(PagesApplication.class, args);
	}
	
	@Bean
	public IModelFactory modelFactory(){
		return new ModelicaModelFactory();
	}
}
