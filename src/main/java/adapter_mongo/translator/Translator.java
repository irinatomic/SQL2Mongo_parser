package adapter_mongo.translator;

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
 * select first_name, last_name from employees join departments using (department_id) where salary > 50 order by first_name
 * select last_name, first_name, departments.department_name from employees join departments using (department_id)
 * select last_name, first_name, departments.department_name, locations.city
       from employees join departments using (department_id) join locations using (location_id)
 * select department_name from departments d join employees e on
	  (e.employee_id = d.manager_id) where employees.last_name like 'H%'
 * select last_name, first_name from employees e1
      join departments d using (department_id) join employees e2 on (d.employee_id = e2.manager_id)
 * select last_name, first_name from employees join departments using (department_id)
      group by last_name, first_name
 * select last_name, first_name, max(salary) from employees
       group by last_name, first_name
 */

public abstract class Translator {

    public abstract void translate(Query query);
}
