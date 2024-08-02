package infraestructure.repository;

import domain.Candidate;
import domain.Election;
import domain.ElectionRepository;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import io.quarkus.redis.datasource.sortedset.ScoreRange;
import io.quarkus.redis.datasource.sortedset.ScoredValue;
import io.quarkus.redis.datasource.sortedset.SortedSetCommands;

import java.util.Map;
import java.util.stream.Collectors;

public class RedisElectionRepository implements ElectionRepository {

    private final SortedSetCommands<String,String> commands;

    private final PubSubCommands<String> stringPubSubCommands;

    public RedisElectionRepository(RedisDataSource redisDataSource){
        commands = redisDataSource.sortedSet(String.class, String.class);
        stringPubSubCommands =redisDataSource.pubsub(String.class);
    }

    @Override
    public void submit(Election election) {
        Map<String, Double> rank = election.votes().entrySet().stream().collect(Collectors.toMap(entry->entry.getKey().id(), entry-> entry.getValue().doubleValue()));
        commands.zadd("election:"+election.id(),rank);
        stringPubSubCommands.publish("elections",election.id());

    }

    public Election sync(Election election){
        var entries = commands.zrangebyscoreWithScores("election:" + election.id(), ScoreRange.from(Integer.MIN_VALUE, Integer.MAX_VALUE)).stream().map(scored->{
            Candidate candidate = election.votes().keySet().stream().filter(c->c.id().equals(scored.value())).findFirst().orElseThrow();
            return Map.entry(candidate, (int) scored.score());
        }).toArray(Map.Entry[]::new);
        return new Election(election.id(), Map.ofEntries(entries));
    }
}
