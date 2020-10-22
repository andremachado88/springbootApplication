package curso.springboot.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan(basePackages="curso.springboot.model")
@ComponentScan(basePackages = {"curso.*"})
@EnableJpaRepositories(basePackages = {"curso.springboot.repository"})
@EnableTransactionManagement
@EnableWebMvc
@RestController
@EnableAutoConfiguration
public class SpringbootApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
		
		
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// Method to implements Cors permissions 
		// allow all end-points
//		registry.addMapping("/**");
		
//		registry.addMapping("/usuario/**").allowedMethods("POST", "PUT", "DELETE").allowedOrigins("www.uol.com.br");
//		registry.addMapping("/usuario/**").allowedMethods("*").allowedOrigins("*");
		// allow all end-points into /usuario/
		registry.addMapping("/usuario/**").allowedMethods("POST", "PUT", "DELETE", "GET");
	}
}
