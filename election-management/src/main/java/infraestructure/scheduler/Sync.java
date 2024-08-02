package infraestructure.scheduler;

import infraestructure.repository.RedisElectionRepository;
import infraestructure.repository.SQLElectionRepository;
import io.quarkus.scheduler.Scheduled;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Sync {
    private final SQLElectionRepository repository;
    private final RedisElectionRepository redisRepository;

    public Sync(SQLElectionRepository repository, RedisElectionRepository redisRepository) {
        this.repository = repository;
        this.redisRepository = redisRepository;
    }

    @Scheduled(cron = "*/5 * * * * ?")
    void syncWorker(){
        repository.findAll().forEach(election -> repository.sync(redisRepository.sync(election)));
    }
}
