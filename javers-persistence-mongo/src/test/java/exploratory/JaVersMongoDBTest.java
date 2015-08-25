package com.nasdaq.xcsd.audit.logger.exploratory;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import lombok.Builder;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.repository.mongo.MongoRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.persistence.Id;

/**
 * Created by patlin on 2015-08-24.
 */
public class JaVersMongoDBTest {
    public static final long ID_ONE_BILLION = 1000000000L;
    public static final long ID_ONE_TRILLION = 1000000000L*1000;

    private Javers javers;
    @Before
    public void setup() {
        MongoDatabase mongoDb = new MongoClient( "localhost" ).getDatabase("test");

        MongoRepository mongoRepo = new MongoRepository(mongoDb);
        javers = JaversBuilder.javers().registerJaversRepository(mongoRepo).build();
    }

    @Builder
    public static class MyEntity {
        @Id
        final private Long id;
        final private String name;
    }
    @Test
    // This test passes

    public void verifyMappingOfLargeId() {
        javers.commit("kent", MyEntity.builder().id(ID_ONE_BILLION).name("red").build());
        javers.commit("kent", MyEntity.builder().id(ID_ONE_BILLION).name("blue").build());
    }
    @Ignore
    @Test
    // This test fails

    public void verifyMappingOfLargerId() {
        javers.commit("kent", MyEntity.builder().id(ID_ONE_TRILLION).name("red").build());
        javers.commit("kent", MyEntity.builder().id(ID_ONE_TRILLION).name("blue").build());
    }
}
