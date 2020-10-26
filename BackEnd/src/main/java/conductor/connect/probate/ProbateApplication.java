package conductor.connect.probate;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class ProbateApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProbateApplication.class, args);
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return (args) -> {
			log.info("Running on " + System.getProperty("os.name"));
		};
	}

}
