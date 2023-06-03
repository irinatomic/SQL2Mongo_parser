[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-8d59dc4de5201274e310e4c54b9627a8934c3b88527886e3b421487c677d23eb.svg)](https://classroom.github.com/a/6hx3LrEQ)

**Clanovi:** \
Luka Labalo 82/22 RN \
Irina Tomic 72/22 RN

**Kredencijali:** \
bp_tim_58	\
writer      \
KVmBBnYAK7HZjs4E	

Our code accepts an sql select statement: 

| clause   | parameters                                                     |
|----------|----------------------------------------------------------------|
| SELECT   | multiple parameters with aggregate functions, no aliases       |
 | FROM     | up to 2 joins                                                  |
 | WHERE    | either multiple inequalities or 1 simple subquery ('=' and 'in') |
| GROUP BY | multiple parameters                                            |
| ORDER BY | multiple parameters asc and desc                               |

 **The query cannot at the same time have a join and a subquery.** \
 It complicates the mongo query (introduces a $pipeline to our $aggregate pipeline). 

Subqueries are simple, like: \
SELECT A FROM B WHERE C = 10

