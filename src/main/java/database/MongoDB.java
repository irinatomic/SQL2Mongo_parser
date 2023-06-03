package database;

import adapter.AdapterImpl;
import com.mongodb.*;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import database.data.Row;
import interfaces.ApplicationFramework;
import org.bson.Document;
import database.utils.Constants;
import org.bson.codecs.DocumentCodec;

import java.util.*;


/* IDEA
String pipeline = "[ " +
                "{ $group: { _id: { department_id: '$department_id', job_id: '$job_id' }, minSalary: { $min: '$salary' }, first_name: { $first: '$first_name' } } }, " +
                "{ $project: { department_id: '$_id.department_id', job_id: '$_id.job_id', minSalary: 1, first_name: 1, _id: 0 } }, " +
                "{ $sort: { ana: 1 } } " +
                "]";
    List<Document> pipelineDocuments = Document.parse(pipeline);
    AggregateIterable<Document> result = collection.aggregate(pipelineDocuments);
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
    public List<Row> runQuery() {

        // Documents for mongo query are in the AdapterImpl
        AdapterImpl adapter = (AdapterImpl) ApplicationFramework.getInstance().getAdapter();
        String collectionName = adapter.getCollectionName();

        connectToDatabase();
        MongoDatabase database = connection.getDatabase(Constants.MYSQL_DB);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        String pipeline = "[ ";
        for(String stage : adapter.getStages()) {
            if(!stage.equals(""))
                pipeline += stage + ", ";
        }
        pipeline += " ]";

        System.out.println("PIPELINE: \n" + pipeline);

//        List<Document> pipelineDocuments = new ArrayList<>();
//        pipelineDocuments.add(Document.parse(pipeline));
        //List<Document> pipelineDocuments = (List<Document>) Document.parse(pipeline, new DocumentCodec()).get("root", List.class);
        List<Document> pipelineDocuments = (List<Document>) Document.parse(pipeline);
        AggregateIterable<Document> result = collection.aggregate(pipelineDocuments);
        MongoCursor<Document> cursor = result.iterator();

        // pack every document into a row
        List<Row> rows = new ArrayList<>();
        while (cursor.hasNext()){
            Row row = new Row();
            row.setName(collectionName);
            Document d = cursor.next();
            for (Map.Entry<String, Object> entry : d.entrySet()) {
                String name = entry.getKey();
                Object value = entry.getValue();
                row.addField(name, value);
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
