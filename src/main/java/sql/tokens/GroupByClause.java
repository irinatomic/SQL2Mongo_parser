package sql.tokens;

import lombok.Getter;
import java.util.*;

@Getter
public class GroupByClause {

    private String originalText;
    private Map<String, String> parameters;            //name, table

    public GroupByClause() {
        this.parameters = new LinkedHashMap<>();        //to keep the order they were added in
    }

    public void parseQueryToSQLObject(String query) {
        this.originalText = query;
        query = query.replace("GROUP BY", "");
        query += ",";

        String[] params = query.split(",");
        for(String p : params){
            String table = null;
            String name = p.trim();

            if(p.contains(".")){
                p += ".";
                table = p.split("\\.")[0].trim();
                name = p.split("\\.")[1].trim();
            }

            this.parameters.put(name, table);
        }
    }

    public boolean containsColumn(String columnName){
        columnName = columnName.trim();
        return parameters.keySet().contains(columnName);
    }
}
