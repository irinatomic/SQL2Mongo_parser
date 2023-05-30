package sql.implementation;

import sql.composite.Token;
import sql.implementation.helpers.Join;
import java.util.*;

/* We will remember the starting table (one we're doing the query against)
*  Every join translates to a $lookup in Mongo -> list of Joins
*  So every join contains:
*  - name of table 2
*  - argument from table 1
*  - argument from table 2
* */

public class FromClause extends Token {

    private String table;                               //table against which we're doing the query
    List<Join> joins;

    public FromClause(Token parent) {
        super(parent);
        this.joins = new ArrayList<>();
    }

    @Override
    public void parseQueryToSQLObject(String query) {
        System.out.println(query);
    }
}
