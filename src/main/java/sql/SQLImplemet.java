package sql;

import interfaces.SQL;
import lombok.Getter;
import sql.tokens.Query;

@Getter
public class SQLImplemet implements SQL {

    private Query currQuery;
    private Query currSubquery;

    @Override
    public void parseQueryToSQLObject(String query) {
        this.currQuery = new Query();
        currQuery.parseQueryToSQLObject(query);
    }

    @Override
    public Query parseSubqueryToSQLObject(String query){
        Query q = new Query();
        q.parseQueryToSQLObject(query);
        this.currSubquery = q;
        return q;
    }
}
