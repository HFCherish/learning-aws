package learning.aws.infrastructure;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import com.google.inject.AbstractModule;
import learning.aws.domain.OrdersRepo;

import java.util.Properties;

import static java.util.Arrays.asList;

public class Models extends AbstractModule {

    private final String environmentId;

    private final Properties properties;


    public Models(String environment) {
        this(environment, new Properties());
    }


    public Models(String environment, Properties properties) {
        this.environmentId = environment;
        this.properties = properties;
    }

    @Override
    protected void configure() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion("us-east-2")
//                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("https://ztumkkb06d.execute-api.us-east-2.amazonaws.com/Prod", "us-east-2"))
                .build();
        DynamoDB dynamoDB = new DynamoDB(client);


//        initTables(dynamoDB);

//        String dbname = System.getenv().getOrDefault("MONGODB_DATABASE", "mongodb_store");
//        String host = System.getenv().getOrDefault("MONGODB_HOST", "localhost");
//        String port = System.getenv().getOrDefault("MONGODB_PORT", "27017");
//        String username = System.getenv().getOrDefault("MONGODB_USER", "admin");
//        String password = System.getenv().getOrDefault("MONGODB_PASS", "mypass");
//        String connectURL = String.format(
//                "mongodb://%s:%s@%s:%s/%s",
//                username,
//                password,
//                host,
//                port,
//                dbname
//        );
//        MongoClient mongoClient = null;
//        try {
//            mongoClient = new MongoClient(
//                    new MongoClientURI(connectURL));
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        DB db = mongoClient.getDB(dbname);
//        Jongo jongo = new Jongo(db);
//        bind(Jongo.class).toInstance(jongo);
//        bind(ProductRepository.class).to(ProductRepositoryImpl.class);
//        bind(ProductMapper.class).to(ProductDao.class);
//        bind(UserRepository.class).to(UserRepositoryImpl.class);
//        bind(UserMapper.class).to(UserDao.class);
//        bind(OrderMapper.class).to(OrderDao.class);
//        requestStaticInjection(SafeInjector.class);
//        bind(OrderValidator.class);
        bind(DynamoDB.class).toInstance(dynamoDB);
        bind(OrdersRepo.class).to(OrdersRepoImpl.class);
    }

    private void initTables(DynamoDB dynamoDB) {

        try {
            System.out.println("Attempting to create table; please wait...");
            Table table = dynamoDB.createTable(DBConstants.ORDERS_TABLE,
                    asList(new KeySchemaElement("id", KeyType.HASH)), // Sort key
                    asList(new AttributeDefinition("user", ScalarAttributeType.S),
                            new AttributeDefinition("id", ScalarAttributeType.S),
                            new AttributeDefinition("amount", ScalarAttributeType.N)),
                    new ProvisionedThroughput(10L, 10L));
            table.waitForActive();
            System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

        } catch (Exception e) {
            System.err.println("Unable to create table: ");
            System.err.println(e.getMessage());
        }
    }

}

