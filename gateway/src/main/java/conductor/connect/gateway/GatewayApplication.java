package conductor.connect.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(r -> r.path("/registrty/**")
						.uri("http://192.168.0.18:8761/"))
				.route(r -> r.path("/jwt/**")
						.filters(f -> f.rewritePath("/jwt", ""))
						.uri("http://192.168.0.18:8541/"))
				.route(r -> r.path("/pocapi/**")
						.filters(f -> f.rewritePath("/pocapi", ""))
						.uri("http://192.168.0.18:9090/"))
				.build();
	}

}
