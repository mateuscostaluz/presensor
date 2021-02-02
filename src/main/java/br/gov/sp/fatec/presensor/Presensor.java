package br.gov.sp.fatec.presensor;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "br.gov.sp.fatec.presensor")
@EntityScan(basePackages = "br.gov.sp.fatec.presensor.model")
public class Presensor {

	public static void main(String[] args) {
		SpringApplication.run(Presensor.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
