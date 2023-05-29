package sql.implementation;

import sql.composite.Token;

import java.util.ArrayList;
import java.util.List;

public class GroupByClause extends Token {

    private List<String> parameters;

    public GroupByClause(Token parent) {
        super(parent);
        this.parameters = new ArrayList<>();
    }

}
