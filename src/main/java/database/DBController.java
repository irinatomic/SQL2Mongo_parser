package database;

import interfaces.Database;

public class DBController implements Database {

    private MongoDB instance;

    @Override
    public void preformQuery(String query) {
        instance = MongoDB.getInstance();
        instance.runQuery(query);
    }
}
