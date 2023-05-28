package gui.view;

import javax.swing.*;

public class CommandBar extends JToolBar {

    public CommandBar(){
        super(HORIZONTAL);
        setFloatable(false);

        add(Box.createHorizontalGlue());
        add(MainFrame.getInstance().getActionManager().getSubmitAction());
        add(Box.createHorizontalGlue());
    }
}
