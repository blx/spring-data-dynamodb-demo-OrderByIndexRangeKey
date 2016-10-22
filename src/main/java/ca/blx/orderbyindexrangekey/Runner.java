package ca.blx.orderbyindexrangekey;

import java.util.List;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

@Component
@ComponentScan(basePackages = {"ca.blx.orderbyindexrangekey"})
@EnableDynamoDBRepositories
public class Runner {

    final String dynamoDBEndpoint = "http://localhost:7000";

    @Autowired
    private Repository modelRepository;

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Runner.class);
        context.refresh();

        Runner r = context.getBean(Runner.class);
        r.run();
    }

    void run() {
        final String threadId = "abc";

        List<Model> models = modelRepository.findByThreadIdOrderByCreatedDesc(threadId);
//        List<Model> models = modelRepository.findByThreadIdAndCreated(threadId, 5L);

        models
                .stream()
                .map(Model::getMessageId)
                .forEach(System.out::println);
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDB dynamoDB = new AmazonDynamoDBClient();

        dynamoDB.setEndpoint(dynamoDBEndpoint);

        return dynamoDB;
    }

}
