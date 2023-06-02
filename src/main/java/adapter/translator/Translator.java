package adapter.translator;

import sql.tokens.Query;

/*  Mongo aggregation pipeline: https://www.tutorialsteacher.com/mongodb/aggregation
 *  SQL             ->       Mongo
 *  Select          :        $group + $project
 *  Order by        :        $order
 *  From            :        main table is the collection
 *  Join            :        $lookup + $match
 */

public abstract class Translator {

    public abstract void translate(Query query);
}
