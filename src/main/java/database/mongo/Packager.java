package database.mongo;

import com.mongodb.client.MongoCursor;
import database.data.Row;
import lombok.Getter;
import org.bson.Document;

import java.util.*;

@Getter
public class Packager {

    public static List<Row> packageReply(MongoCursor<Document> cursor, String collectionName){

        // pack every document into a row
        List<Row> rows = new ArrayList<>();
        while (cursor.hasNext()){
            Row row = new Row();
            row.setName(collectionName);
            Document d = cursor.next();
            for (Map.Entry<String, Object> entry : d.entrySet()) {
                String name = entry.getKey();
                Object value = entry.getValue();
                row.addField(name, value);
                //System.out.println("NAME " + name + " VALUE " + value);
            }
            rows.add(row);
        }

        System.out.println("ROW NO: " + rows.size());
        return rows;
    }
}
