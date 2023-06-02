package interfaces;

import sql.tokens.Query;

public interface Adapter {

    void adaptQueryForMongo(Query query);
}
