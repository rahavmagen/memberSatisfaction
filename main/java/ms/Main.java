package ms;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * Main class.
 *
 */
@Component
@ApplicationPath("/ws")
public class Main extends ResourceConfig {
	public Main() {
		register(MyResource.class);
	}
}

