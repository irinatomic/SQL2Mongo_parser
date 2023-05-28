package data;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class Row {

    private String name;
    private Map<String, Object> fields;

    public Row(){
        this.fields = new LinkedHashMap<>();
    }

    public void addField(String fieldName, Object value){
        this.fields.put(fieldName, value);
    }

    public void removeField(String fieldName){
        this.fields.remove(fieldName);
    }
}
