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
        query = query.replace("[", "").replace("]", "");
        query += ",";
        String[] params = query.split(",");
        Arrays.setAll(params, i -> params[i].trim());

        for(String p : params){
            p += " ";
            String[] paramParts = p.split(" ");

            int order = 1;
            if(paramParts.length == 2 && paramParts[1].equalsIgnoreCase("DESC"))
                order = -1;
            this.parameters.put(paramParts[0], order);
        }

    }
}
