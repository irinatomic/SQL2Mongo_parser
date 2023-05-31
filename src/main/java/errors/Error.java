package errors;

/* Rule 1: no mandatory SELECT and FROM
*  Rule 2: all select parameters (not aggregate functions) are in GROUP BY
*  Rule 3: aggregate functions must go in HAVING, not in WHERE
*  Rule 4: JOIN clauses must have either USING or ON
 */
public enum Error {

    NO_MANDATORY_CLAUSES,
    GROUP_BY_ERROR,
    AGGREGATE_FUNCTION_IN_WHERE,
    NO_JOIN_CONDITION
}
