package sql.implementation;

import lombok.Getter;
import sql.architecture.Token;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public class GroupByClause extends Token {

    private String originalText;
    private List<String> parameters;

    public GroupByClause(Token parent) {
        super(parent);
        this.parameters = new ArrayList<>();
    }

    @Override
    public void parseQueryToSQLObject(String query) {
        this.originalText = query;
        query = query.replace("GROUP BY", "");
        query += ",";

        String[] params = query.split(",");
        this.parameters = Arrays.stream(params).map(String::trim).collect(Collectors.toList());
    }
}
