package database;

import database.data.Row;
import interfaces.Database;
import java.util.List;

/* IDEA
String pipeline = "[ " +
                "{ $group: { _id: { department_id: '$department_id', job_id: '$job_id' }, minSalary: { $min: '$salary' }, first_name: { $first: '$first_name' } } }, " +
                "{ $project: { department_id: '$_id.department_id', job_id: '$_id.job_id', minSalary: 1, first_name: 1, _id: 0 } }, " +
                "{ $sort: { ana: 1 } } " +
                "]";
    List<Document> pipelineDocuments = Document.parse(pipeline);
    AggregateIterable<Document> result = collection.aggregate(pipelineDocuments);
 */

public class DBController implements Database {

    private MongoDB instance;

    @Override
    public List<Row> preformQuery(String query) {
        instance = MongoDB.getInstance();
        return instance.runQuery(query);
    }
}
