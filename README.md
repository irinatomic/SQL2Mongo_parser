## SQL to Mongo parser

This project is a part of the "Databases' university subject. This is a simple app where the user can \
query the Mongo database using SQL. Our task was to give an object  representation of an SQL query, \
validate it and parse it to a Mongo query. Then we query the Mongo database and show the results.

<img src="src/main/resources/images/db_app.png"
alt="Sql to Mongo parser"
style="float: left; margin-right: 10px;" />

### Our pipeline

The pipeline can be seen in the app/AppCore file. It consists of: 
1. Representing the query as an SQL object (sql package) 
2. Validating the query (validator package) 
3. Adapting the query for Mongo (adapter package) 
4. Querying the Mongo DB (database package)

### Certain limitations of the project

If we wish to show a column that is not from a table we're querying, the table name needs \
to be given as well. Example:
```
select last_name, departments.department_name, locations.street_address 
from hr.employees join departments using (department_id) join locations using (location_id)
where last_name like 'P%'
```

Our code accepts an sql select statement: 

| clause   | parameters                                                       |
|----------|------------------------------------------------------------------|
| SELECT   | multiple parameters with aggregate functions, no aliases         |
 | FROM     | up to 2 joins                                                    |
 | WHERE    | either multiple inequalities or 1 simple subquery ('=' and 'in') |
 | HAVING   | not supported                                                    |
| GROUP BY | multiple parameters                                              |
| ORDER BY | multiple parameters asc and desc                                 |

 **The query cannot at the same time have a join and a subquery.** \
 It complicates the mongo query (introduces a $pipeline to our $aggregate pipeline). 

Subqueries are simple, like: \
SELECT A FROM B WHERE C = 10

### Examples of the processed queries
```
select first_name, last_name from employees where department_id in (
 select department_id from employees where employee_id = 103 or employee_id = 156)

db.employees.aggregate( 
    { $lookup: {
	     from: "employees", 
	     localField: "department_id", 
	     foreignField: "department_id", 
	     as: "result" 
     }
    }
    { $unwind: "$result" }, 
    { $match: { 
        $or: [ { "result.employee_id": {$eq: 103} }, { "result.employee_id": {$eq: 156} } ] } 
     }, 
    { $project: {
        first_name: 1, 
        last_name: 1, 
        _id: 0 
        } 
    }
)
```
```
select job_id, avg(salary) from employees group by job_id

db.employees.aggregate(
    { $project: 
        {job_id: "$_id.job_id", avgsalary: 1, _id: 0 } 
    }
)
```
```
select last_name, department_name, street_address from hr.employees join hr.departments using (department_id)
join hr.locations using (location_id) where last_name like 'P%'

db.employees.aggregate( 
    { $lookup: {
        from: "hr.departments",
        localField: "department_id",
        foreignField: "department_id",
        as: "result1"
     }
    },
    { $unwind: "$result1" }, 
    {
    $lookup: {
        from: "hr.locations",
        localField: "location_id",
        foreignField: "location_id",
        as: "result2"
     }
    },
    { $unwind: "$result2" }, 
    { $match: { 
        last_name: {$regex: /^p.*/i} 
     } 
    }, 
    { $project: {
        last_name: 1, 
        department_name: 1, 
        street_address: 1, 
        _id: 0 
     } 
    }
)
```
