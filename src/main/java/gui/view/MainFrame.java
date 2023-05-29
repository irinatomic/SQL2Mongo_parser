package gui.view;

import app.AppCore;
import gui.controller.ActionManager;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class MainFrame extends JFrame {

    private static MainFrame instance;
    private AppCore appCore;
    private ActionManager actionManager;
    private TextArea inputText;
    private CommandBar commandBar;
    private JScrollPane tableScrollPane;
    private JTable jTable;

    private MainFrame(){ }

    public static MainFrame getInstance(){
        if(instance == null){
            instance = new MainFrame();
            instance.initialise();
        }
        return instance;
    }

    private void initialise(){
        actionManager = new ActionManager();
        initialiseGui();
    }

    private void initialiseGui(){
        initElements();
        addElements();

        this.setTitle("SQL to NOSQL converter");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void initElements(){
        inputText = new TextArea("Type your SQL query here");
        commandBar = new CommandBar();
        jTable = new JTable();
        jTable.setPreferredScrollableViewportSize(new Dimension(500, 300));
        jTable.setFillsViewportHeight(true);
        jTable.setModel(AppCore.getInstance().getTableModel());

        tableScrollPane = new JScrollPane(jTable);
    }

    private void addElements(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(inputText, BorderLayout.NORTH);
        panel.add(commandBar, BorderLayout.CENTER);
        panel.add(tableScrollPane, BorderLayout.SOUTH);
        add(panel);
    }

    public void setAppCore(AppCore appCore){
        this.appCore = appCore;
    }

    public void updateTable(){
        jTable.setModel(AppCore.getInstance().getTableModel());
    }

}
