package sql.implementation;

import sql.composite.Token;

/* Can have up to 32 parameters
*  from hr.employees e join hr.departments d using (department_id) join hr.locations l using (location_id)
*  -> will translate to 2 $lookup in the Mongo query
* */
public class FromClause extends Token {



    public FromClause(Token parent) {
        super(parent);
    }

}
