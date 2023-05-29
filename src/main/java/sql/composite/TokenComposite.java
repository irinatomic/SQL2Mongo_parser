package sql.composite;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

// nasledivo (Query and WhereClause)

@Getter
@Setter
public abstract class TokenComposite extends Token {

    protected List<Token> children;

    public TokenComposite(Token parent) {
        super(parent);
        this.children = new ArrayList<>();
    }

    public void removeChild(Token child){
        if(child != null && !children.isEmpty())
            children.remove(child);
    }

    public void addChild(Token child){
        children.add(child);
    }
}
