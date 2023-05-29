package sql.composite;

import lombok.Getter;
import lombok.Setter;

/* Composite pattern: klasa koja je ujedno i natklasa a kad se instancira je nenaslediva (leaf)
*  Koristimo da bi podrzali subquerije u okviru klauzule WHERE.
* */

@Getter
@Setter
public class Token {

    private Token parent;

    public Token(Token parent){
        this.parent = parent;
    }

}
