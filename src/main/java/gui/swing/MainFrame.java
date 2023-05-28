package gui.swing;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static MainFrame instance;
    private JButton submitButton;
    private TextArea inputText;
    private JTable jTable;

    private MainFrame(){ }

    private void initialise(){

        this.setTitle("SQL to NOSQL converter");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        submitButton = new JButton("Submit query");
        inputText = new TextArea();
        initTable();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(inputText, BorderLayout.NORTH);
        panel.add(submitButton, BorderLayout.CENTER);
        panel.add(jTable, BorderLayout.SOUTH);
        add(panel);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void initTable(){
        jTable = new JTable();
        jTable.setPreferredScrollableViewportSize(new Dimension(500, 400));
        jTable.setFillsViewportHeight(true);
        this.add(new JScrollPane(jTable));
    }

    public static MainFrame getInstance(){
        if(instance == null){
            instance = new MainFrame();
            instance.initialise();
        }
        return instance;
    }
}
