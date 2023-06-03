package sql.tokens;

import lombok.Getter;
import sql.tokens.helpers.SelectParameter;
import java.util.*;

@Getter
public class SelectClause {

    private String originalText;
    private List<SelectParameter> parameters;

    public SelectClause() {
        this.parameters = new ArrayList<>();
    }

    public void parseQueryToSQLObject(String query) {
        this.originalText = query;
        query = query.replace("[", "").replace("]", "");
        query += ",";
        String[] params = query.split(",");

        for(String p : params){
            SelectParameter sp = new SelectParameter(p.trim());
            this.parameters.add(sp);
        }
    }

    public boolean hasAnAggregateParameter(){
        for(SelectParameter sp : this.parameters){
            if(sp.getAggregateFunction() != null)
                return true;
        }
        return false;
    }
}
