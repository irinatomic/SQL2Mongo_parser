package sql.composite;

import lombok.Getter;
import java.util.*;

// nasledivo (only Query)
// where clause has WhereParameters that has Inequalities
// that Inequality can have a subquery

@Getter
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
