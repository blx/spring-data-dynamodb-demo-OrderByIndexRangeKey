package ca.blx.orderbyindexrangekey;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.DynamoDBPagingAndSortingRepository;

public interface Repository extends DynamoDBPagingAndSortingRepository<Model, String> {

    // Find by GSI hash key + range key works.
    List<Model> findByThreadIdAndCreated(String threadId, Long created);

    // But find by GSI hash key, sorted by GSI range key doesn't work.
    List<Model> findByThreadIdOrderByCreatedDesc(String threadId);

}
