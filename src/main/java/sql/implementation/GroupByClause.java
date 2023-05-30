package sql.implementation;

import lombok.Getter;
import sql.composite.Token;
import java.util.*;

@Getter
public class GroupByClause extends Token {

    private List<String> parameters;

    public GroupByClause(Token parent) {
        super(parent);
        this.parameters = new ArrayList<>();
    }

    @Override
    public void parseQueryToSQLObject(String query) {

    }
}
