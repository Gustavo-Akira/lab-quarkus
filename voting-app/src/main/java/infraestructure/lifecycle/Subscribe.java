package infraestructure.lifecycle;

import domain.Election;
import infraestructure.repository.RedisElectionRepository;
import io.quarkus.redis.datasource.ReactiveRedisDataSource;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.runtime.Startup;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;

@Startup
@ApplicationScoped
public class Subscribe {
    private static final Logger LOGGER = LoggerFactory.getLogger(Subscribe.class);


    public Subscribe(ReactiveRedisDataSource dataSource, RedisElectionRepository repository){
        LOGGER.info("Subscribe");
        Multi<String> multi = dataSource.pubsub(String.class).subscribe("elections");
        multi.emitOn(Infrastructure.getDefaultWorkerPool())
        .subscribe().with(id->{
            LOGGER.info("election "+id+" received");
            Election election = repository.findById(id);
            LOGGER.info("election "+id+" starting");
        });
    }
}
