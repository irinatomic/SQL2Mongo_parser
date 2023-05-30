package sql.composite;

import lombok.Getter;
import lombok.Setter;
import sql.SQLImplemet;

/* Composite pattern: klasa koja je ujedno i natklasa a kad se instancira je nenaslediva (leaf)
*  Koristimo da bi podrzali subquerije u okviru klauzule WHERE.
*  Decu mogu imati Query i WhereClause
* */

@Getter
@Setter
public abstract class Token extends SQLImplemet {

    private Token parent;

    public Token(Token parent){
        this.parent = parent;
    }

}
