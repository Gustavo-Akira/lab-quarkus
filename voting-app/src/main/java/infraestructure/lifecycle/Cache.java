package infraestructure.lifecycle;

import infraestructure.repository.RedisElectionRepository;
import io.quarkus.runtime.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@Startup
@ApplicationScoped
public class Cache {
    private static final Logger LOGGER = LoggerFactory.getLogger(Cache.class);

    public Cache(RedisElectionRepository repository){
        LOGGER.info("Setup: Cache");
        repository.findAll();
    }
}
