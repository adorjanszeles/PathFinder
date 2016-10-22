package pathfinder.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pathfinder.controller.PathfinderController;

import java.io.IOException;

/**
 * Az alkalmazást indító osztály.
 *
 * @author Széles Adorján
 * Date: 2016. 10. 22.
 */
@SpringBootApplication
public class Application {
    /**
     * Belépési pont.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        SpringApplication.run(PathfinderController.class, args);
    }
}
