package br.gov.sp.fatec.presensor;

import br.gov.sp.fatec.presensor.services.DateTimeServices;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "br.gov.sp.fatec.presensor")
@EntityScan(basePackages = "br.gov.sp.fatec.presensor.model")
public class PresenceControlUsingBluetoothBeaconApplication {

	public static void main(String[] args) {
		// SpringApplication.run(PresenceControlUsingBluetoothBeaconApplication.class, args);

		String dts = DateTimeServices.getDateTimeFormatted();
		Integer dow = DateTimeServices.getDayOfWeek();

		System.out.println(dts);
		System.out.println(dow);

	}

}
