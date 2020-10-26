package conductor.connect.probate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
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
			System.out.println(System.getProperty("os.name"));
		};
	}

}
