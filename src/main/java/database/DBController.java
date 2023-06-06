package database;

import database.data.Row;
import database.mongo.Executor;
import interfaces.Database;
import java.util.List;

public class DBController implements Database {

    private Executor instance;

    @Override
    public List<Row> preformQuery() {
        instance = Executor.getInstance();
        return instance.runQuery();
    }
}
