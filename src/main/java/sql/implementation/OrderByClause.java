package sql.implementation;

import lombok.Getter;
import sql.composite.Token;
import java.util.*;

@Getter
public class OrderByClause extends Token {

    //int: 1 (ASC) or -1 (DESC)
    private Map<String, Integer> parameters;

    public OrderByClause(Token parent) {
        super(parent);
        this.parameters = new LinkedHashMap<>();       //linked - so the param. keep the order thay are added in
    }

    @Override
    public void parseQueryToSQLObject(String query) {

    }
}
