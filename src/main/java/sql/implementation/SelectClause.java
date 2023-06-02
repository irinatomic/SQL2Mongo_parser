package sql.implementation;

import lombok.Getter;
import sql.implementation.helpers.SelectParameter;
import sql.architecture.Token;
import java.util.*;

@Getter
public class SelectClause extends Token {

    private String originalText;
    private List<SelectParameter> parameters;

    public SelectClause(Token parent) {
        super(parent);
        this.parameters = new ArrayList<>();
    }

    @Override
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
}
