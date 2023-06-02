package interfaces;

import sql.tokens.Query;

public interface SQL {

    void parseQueryToSQLObject(String query);
    Query parseSubqueryToSQLObject(String query);
}
