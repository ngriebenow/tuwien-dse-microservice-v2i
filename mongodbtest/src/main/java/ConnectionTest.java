import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Collection;

public class ConnectionTest {

    public static void main(String[] args) {


        MongoClient mongoClient = MongoClients.create(
                "mongodb+srv://ngriebenow:9qxQ8smtD99PX@dse-mongodb-9ztit.gcp.mongodb.net/test?retryWrites=true&w=majority");
        MongoDatabase database = mongoClient.getDatabase("test");

        database.createCollection("testcollection");

        MongoCollection<Document> testcollection = database.getCollection("testcollection");

        Document doc = new Document();
        doc.put("test","hello world");

        testcollection.insertOne(doc);




    }

}
