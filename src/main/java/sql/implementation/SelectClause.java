package sql.implementation;

import lombok.Getter;
import sql.implementation.helpers.SelectParameter;
import sql.composite.Token;
import java.util.ArrayList;
import java.util.List;

@Getter
public class SelectClause extends Token {

    private List<SelectParameter> parameters;

    public SelectClause(Token parent) {
        super(parent);
        this.parameters = new ArrayList<>();
    }
}
