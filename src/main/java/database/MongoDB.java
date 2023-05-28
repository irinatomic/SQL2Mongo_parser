package database;

import com.mongodb.*;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import utils.Constants;

import java.util.Arrays;

public class MongoDB {

    private static MongoDB instance;
    private MongoClient connection;

    private MongoDB() { }

    public static MongoDB getInstance(){
        if(instance == null)
            instance = new MongoDB();
        return instance;
    }

    public void runQuery(String query) {
        connectToDatabase();

        query = "db.employees.find({})";

//        MongoCursor<Document> cursor = database.getCollection("employees").aggregate(
//                Arrays.asList(
//                        Document.parse("{\n" +
//                                "  $match: {first_name: \"Steven\", last_name: \"King\"}\n" +
//                                "}"),
//                        Document.parse("{\n" +
//                                "  $lookup: {\n" +
//                                "    from: \"employees\",\n" +
//                                "    localField: \"department_id\",\n" +
//                                "    foreignField: \"department_id\",\n" +
//                                "    as: \"employeesInTheSameDepartment\"\n" +
//                                "  }\n" +
//                                "}"),
//                        Document.parse("{ $unwind: \"$employeesInTheSameDepartment\" }"),
//                        Document.parse("{ $project: {\n" +
//                                "    \"employeesInTheSameDepartment.first_name\": 1,\n" +
//                                "    \"employeesInTheSameDepartment.last_name\": 1\n" +
//                                "  }\n" +
//                                "}")
//                )
//        ).iterator();
//
//        while (cursor.hasNext()){
//            Document d = cursor.next();
//            System.out.println(d.toJson());
//        }

        terminateConnection();
    }

    private void connectToDatabase() {
        String username = Constants.MYSQL_USERNAME;
        String password = Constants.MYSQL_PASSWORD;
        String dbName = Constants.MYSQL_DB;
        String ip = Constants.MYSQL_IP;

        MongoCredential credential = MongoCredential.createCredential(username, dbName, password.toCharArray());
        this.connection = new MongoClient(new ServerAddress(ip, 27017), Arrays.asList(credential));
    }

    private void terminateConnection(){
        this.connection.close();
    }
}
