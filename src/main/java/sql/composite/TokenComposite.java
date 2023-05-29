package sql.composite;

import lombok.Getter;
import lombok.Setter;

/* nasledivo (Query and WhereClause)
*  nema lista dece vec samo 1 dete
* */

@Getter
@Setter
public class TokenComposite extends Token {

    protected Token child;

    public TokenComposite(Token parent) {
        super(parent);
    }

}
