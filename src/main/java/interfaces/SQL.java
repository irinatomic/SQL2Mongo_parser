package interfaces;

import sql.implementation.Query;

public interface SQL {

    void parseQueryToSQLObject(String query);
    public Query parseSubqueryToSQLObject(String query);
}
