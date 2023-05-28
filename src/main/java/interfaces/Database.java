package interfaces;

import data.Row;
import java.util.List;

public interface Database {

    List<Row> preformQuery(String query);
}
