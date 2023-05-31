package sql;

import interfaces.SQL;
import lombok.Getter;
import sql.implementation.Query;

@Getter
public class SQLImplemet implements SQL {

    private static Query currQuery;

    public static Query getCurrQuery() {
        return currQuery;
    }

    @Override
    public void parseQueryToSQLObject(String query) {
        this.currQuery = new Query(null);
        currQuery.parseQueryToSQLObject(query);
    }
}
