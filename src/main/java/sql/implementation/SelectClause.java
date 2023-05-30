package sql.implementation;

import lombok.Getter;
import sql.implementation.helpers.SelectParameter;
import sql.composite.Token;
import java.util.*;

@Getter
public class SelectClause extends Token {

    private List<SelectParameter> parameters;

    public SelectClause(Token parent) {
        super(parent);
        this.parameters = new ArrayList<>();
    }

    @Override
    public void parseQueryToSQLObject(String query) {
        query = query.replace("[", "").replace("]", "");
        query += ",";
        String[] params = query.split(",");

        for(String p : params){
            SelectParameter sp = new SelectParameter(p.trim());
            this.parameters.add(sp);
        }
    }
}
