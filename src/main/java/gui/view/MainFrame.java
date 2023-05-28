package gui.view;

import controller.ActionManager;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class MainFrame extends JFrame {

    private static MainFrame instance;
    private ActionManager actionManager;
    private TextArea inputText;
    private CommandBar commandBar;
    private JTable jTable;

    private MainFrame(){ }

    private void initialise(){
        actionManager = new ActionManager();
        initialiseGui();
    }

    private void initialiseGui(){
        this.setTitle("SQL to NOSQL converter");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inputText = new TextArea();
        commandBar = new CommandBar();
        initTable();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(inputText, BorderLayout.NORTH);
        panel.add(commandBar, BorderLayout.CENTER);
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
