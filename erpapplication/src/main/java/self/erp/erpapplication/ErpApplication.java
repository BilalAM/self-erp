package self.erp.erpapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * This is the main spring boot application of erp.
 * It scans all the controllers , models and repositories for the spring application context
 * Just run this as a simple jar
 */
@ComponentScan(basePackages = "self.erp.visitorservice")
@EnableJpaRepositories(basePackages = { "self.erp.visitorservice.repositories" })
@EntityScan(basePackages = { "self.erp.visitorservice.repositories" })
@SpringBootApplication
public class ErpApplication {
    public static void main(String[] args) {
        SpringApplication.run(ErpApplication.class, args);
    }
}
