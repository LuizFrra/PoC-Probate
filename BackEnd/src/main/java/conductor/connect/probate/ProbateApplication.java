package conductor.connect.probate;

import com.fasterxml.jackson.databind.ObjectMapper;
import conductor.connect.probate.Repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProbateApplication {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	public RequestRepository requestRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProbateApplication.class, args);
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}
