package org.raspinloop.server.modelica.modelicaModelService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;


@SpringBootApplication
@Import(org.raspinloop.server.modelica.mdt.Modelica.class)
public class ModelicaModelServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModelicaModelServiceApplication.class, args);
	}
}

