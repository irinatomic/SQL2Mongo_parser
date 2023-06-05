package sql.tokens;

import lombok.Getter;
import sql.tokens.helpers.OrderByParameter;

import java.util.*;

@Getter
public class OrderByClause{

    private List<OrderByParameter> parameters;

    public OrderByClause() {
        this.parameters = new ArrayList<>();
    }

    public void parseQueryToSQLObject(String query) {
        query = query.replace("[", "").replace("]", "");
        query += ",";
        String[] params = query.split(",");
        Arrays.setAll(params, i -> params[i].trim());

        for(String p : params){
            OrderByParameter obp = new OrderByParameter(p);
            this.parameters.add(obp);
        }

    }
}
