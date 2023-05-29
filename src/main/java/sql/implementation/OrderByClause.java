package sql.implementation;

import lombok.Getter;
import sql.composite.Token;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class OrderByClause extends Token {

    private Map<String, Integer> parameters;

    public OrderByClause(Token parent) {
        super(parent);
        this.parameters = new LinkedHashMap<>();       //linked - so the param. keep the order thay are added in
    }
}
