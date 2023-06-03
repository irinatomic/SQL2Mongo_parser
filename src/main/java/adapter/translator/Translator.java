package adapter.translator;

import sql.tokens.Query;

/*  Mongo aggregation pipeline: https://www.tutorialsteacher.com/mongodb/aggregation
 *  $project          :        select
 *  $group            :        group + aggregate func. from select
 *  $lookup + $unwind :        primarily join. subqueries makes one if it doesn't already exist
 *  $match            :        basic having params; subquery adds $match to $lookup
 *  $order            :        order by
 */

/* Test cases:
 * select first_name, last_name, min(salary) order by first_name, last_name DESC
 */

public abstract class Translator {

    public abstract void translate(Query query);
}
