package sql.tokens;

import lombok.Getter;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public class GroupByClause {

    private String originalText;
    private List<String> parameters;

    public GroupByClause() {
        this.parameters = new ArrayList<>();
    }

    public void parseQueryToSQLObject(String query) {
        this.originalText = query;
        query = query.replace("GROUP BY", "");
        query += ",";

        String[] params = query.split(",");
        this.parameters = Arrays.stream(params).map(String::trim).collect(Collectors.toList());
    }
}
