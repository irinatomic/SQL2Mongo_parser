package adapter.translator;

import sql.tokens.Query;

/*  Mongo aggregation pipeline: https://www.tutorialsteacher.com/mongodb/aggregation
 *  $project          :        select
 *  $group            :        group + aggregate func. from select
 *  $lookup + $unwind :        either for joins or for a subquery
 *  $match            :        basic where inequalities
 *  $order            :        order by
 */

/* Test cases:
 * select first_name, last_name, min(salary) order by first_name, last_name DESC
 * select first_name, last_name from employees join departments using (department_id)
 *        where salary > 50 order by first_name
 */

public abstract class Translator {

    public abstract void translate(Query query);
}
