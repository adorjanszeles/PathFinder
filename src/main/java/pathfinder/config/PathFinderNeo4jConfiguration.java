package pathfinder.config;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@EnableAutoConfiguration
@ComponentScan(basePackages = { "pathfinder.services" })
@Configuration
@EnableNeo4jRepositories(basePackages = "pathfinder.model.repositories")
public class PathFinderNeo4jConfiguration extends Neo4jConfiguration {

	@Bean
	public org.neo4j.ogm.config.Configuration getConfiguration() {
		org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();
		config.driverConfiguration().setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
				.setURI("http://localhost:7474").setCredentials("neo4j", "Jelszo12AA");
		return config;
	}

	@Override
	public SessionFactory getSessionFactory() {
		return new SessionFactory(getConfiguration(), "pathfinder.model.nodes");
	}
}
