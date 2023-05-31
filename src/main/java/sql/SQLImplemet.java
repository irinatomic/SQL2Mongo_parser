package sql;

import interfaces.SQL;
import lombok.Getter;
import sql.implementation.Query;

@Getter
public class SQLImplemet implements SQL {

    private Query currQuery;

    @Override
    public void parseQueryToSQLObject(String query) {
        this.currQuery = new Query(null);
        currQuery.parseQueryToSQLObject(query);
    }

    @Override
    public Query parseSubqueryToSQLObject(String query){
        Query q = new Query(null);
        q.parseQueryToSQLObject(query);
        return q;
       // return null;
    }
}
