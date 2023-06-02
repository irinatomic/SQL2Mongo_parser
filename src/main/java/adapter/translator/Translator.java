package adapter.translator;

import sql.tokens.Query;

public abstract class Translator {

    public abstract void translate(Query query);
}
