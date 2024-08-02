package infraestructure.repository;

import domain.Candidate;
import domain.Election;
import domain.ElectionRepository;
import io.quarkus.cache.CacheResult;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.keys.KeyCommands;
import io.quarkus.redis.datasource.sortedset.SortedSetCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;
@ApplicationScoped
public class RedisElectionRepository implements ElectionRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisElectionRepository.class);

    private final SortedSetCommands<String,String> setCommands;
    private final KeyCommands<String> keyCommands;

    public RedisElectionRepository(RedisDataSource dataSource) {
        this.setCommands = dataSource.sortedSet(String.class, String.class);
        keyCommands = dataSource.key(String.class);
    }


    @Override
    @CacheResult(cacheName = "memoization")
    public Election findById(String id) {
        LOGGER.info("Getting information from redis of election " +id);
        List<Candidate> candidates = setCommands.zrange("election:"+id,0,-1).stream().map(Candidate::new).collect(Collectors.toList());
        return new Election(id, candidates);
    }

    @Override
    public List<Election> findAll() {
        LOGGER.info("Getting all elections");
        return keyCommands.keys("elections:*").stream().map(id->findById(id.replace(":",""))).toList();
    }

    @Override
    public void vote(String electionId, String id) {
        LOGGER.info("Voting for "+id);
        setCommands.zincrby("election:"+electionId,1,id);
    }
}
