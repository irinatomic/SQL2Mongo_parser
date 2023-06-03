package database;

import database.data.Row;
import database.mongo.MongoDB;
import interfaces.Database;
import java.util.List;

public class DBController implements Database {

    private MongoDB instance;

    @Override
    public List<Row> preformQuery() {
        instance = MongoDB.getInstance();
        return instance.runQuery();
    }
}
