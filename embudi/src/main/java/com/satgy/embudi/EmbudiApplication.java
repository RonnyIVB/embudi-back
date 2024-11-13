package com.satgy.embudi;

import com.satgy.embudi.general.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmbudiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmbudiApplication.class, args);
	}

	// le nombro al bean por si en alguna ocasión coincide con el nombre de otra bean, para poder diferenciarlo.
	@Bean("AppProperties")
	public AppProperties getAppProperties(){
		return new AppProperties();
	}
}
