package sql.implementation;

import lombok.Getter;
import sql.composite.Token;
import java.util.ArrayList;
import java.util.List;

@Getter
public class SelectClause extends Token {

    private List<Parameter> parameters;

    @Getter
    public class Parameter {
        //can be accesed by  SelectClause.Parameter p -> p.getName()
        private String name;
        private String alias;
        private String aggregateFunction;
    }

    public SelectClause(Token parent) {
        super(parent);
        this.parameters = new ArrayList<>();
    }
}
