package pathfinder.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import pathfinder.config.PathFinderNeo4jConfiguration;
import pathfinder.ui.configuration.SpringSecurityConfiguration;

import java.io.IOException;

/**
 * Az alkalmazást indító osztály.
 *
 * @author Széles Adorján
 * Date: 2016. 10. 22.
 */
@ComponentScan(basePackages = { "pathfinder.services" })
@Import(value = { PathFinderNeo4jConfiguration.class, SpringSecurityConfiguration.class })
@EnableAutoConfiguration
@SpringBootApplication
public class Application {
    /**
     * Belépési pont.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
