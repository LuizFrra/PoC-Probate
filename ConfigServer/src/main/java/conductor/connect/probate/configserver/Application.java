package conductor.connect.probate.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@EnableConfigServer
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
