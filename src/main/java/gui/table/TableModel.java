package gui.table;

import database.data.Row;
import gui.view.MainFrame;

import javax.swing.table.DefaultTableModel;
import java.util.*;

public class TableModel extends DefaultTableModel {

    private List<Row> rows;

    private void updateModel(){

        int columnCount = rows.get(0).getFields().keySet().size();
        Vector columnVector = DefaultTableModel.convertToVector(rows.get(0).getFields().keySet().toArray());
        Vector dataVector = new Vector(columnCount);

        for (int i=0; i<rows.size(); i++)
            dataVector.add(DefaultTableModel.convertToVector(rows.get(i).getFields().values().toArray()));

        setDataVector(dataVector, columnVector);
        //Create an observable pattern for TableModel and JTable/MainFrame ?
        MainFrame.getInstance().updateTable();
    }

    public void setRows(List<Row> rows){
        this.rows = rows;
        updateModel();
    }
}
