package database;

import com.mongodb.*;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import data.Row;
import org.bson.Document;
import utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    //TODO: argument query will probably be changed into a 'MongoCursor<Document> cursor' which will be created by the Adapter
    public List<Row> runQuery(String query) {

        connectToDatabase();
        MongoDatabase database = connection.getDatabase("bp_tim58");

//        MongoCollection<Document> collection = database.getCollection("employees");
//        FindIterable<Document> results = collection.find(Document.parse("{}"));
//
//        for (Document document : results) {
//            try{
//                System.out.println(document.toJson());
//            } catch (Exception e){
//                terminateConnection();
//            }
//        }

        // send query to db
        MongoCursor<Document> cursor = database.getCollection("employees").aggregate(
                Arrays.asList(
                        Document.parse("{\n" + "$match: {} \n" + "}")
                )
        ).iterator();

        // pack every document into a row
        List<Row> rows = new ArrayList<>();
        while (cursor.hasNext()){
            Row row = new Row();
            row.setName("employees");
            Document d = cursor.next();
            for (Map.Entry<String, Object> entry : d.entrySet()) {
                String name = entry.getKey();
                Object value = entry.getValue();
                row.addField(name, value);
                //System.out.println("Name: " + name + ", Value: " + value);
            }
            rows.add(row);
        }

        terminateConnection();
        return rows;
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
        try{
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            connection = null;
        }
    }
}
