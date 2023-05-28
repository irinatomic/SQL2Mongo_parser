package database;

import com.mongodb.*;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import org.bson.Document;
import utils.Constants;

import java.util.Arrays;

/*
select * from employees =
- db.employees.find({})
- db.employees.aggregate().match({})
 */

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

        MongoDatabase database = connection.getDatabase("bp_tim58");
        MongoCollection<Document> collection = database.getCollection("employees");
        FindIterable<Document> results = collection.find(Document.parse("{}"));

        for (Document document : results) {
            try{
                System.out.println(document.toJson());
            } catch (Exception e){
                //System.out.print(e);
                terminateConnection();
            }
        }

        MongoCursor<Document> cursor = database.getCollection("employees").aggregate(
                Arrays.asList(
                        Document.parse("{\n" + "$match: {} \n" + "}")
                )
        ).iterator();

        while (cursor.hasNext()){
            try{
                Document d = cursor.next();
                System.out.println(d.toJson());
            } catch (Exception e) {
                //System.out.print(e);
                terminateConnection();
            }
        }

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
