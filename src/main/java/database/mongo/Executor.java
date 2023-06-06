package database.mongo;

import adapter.AdapterImpl;
import com.mongodb.*;
import com.mongodb.MongoClient;
import com.mongodb.client.*;
import database.data.Row;
import interfaces.ApplicationFramework;
import org.bson.Document;
import database.utils.Constants;

import java.util.*;

// Querying the mongo db: https://stackoverflow.com/questions/55022849/running-custom-mongodb-queries-in-java

public class Executor {

    private static Executor instance;
    private MongoClient connection;

    private Executor() { }

    public static Executor getInstance(){
        if(instance == null)
            instance = new Executor();
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

        List<Document> forQuery = adapter.getDocs();
//        for(Document d : forQuery)
//            System.out.println(d.toString());
        AggregateIterable<Document> result = collection.aggregate(forQuery);
        MongoCursor<Document> cursor = result.iterator();

        List<Row> rows = Packager.packageReply(cursor, collectionName);
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
