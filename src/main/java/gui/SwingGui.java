package gui;

import gui.swing.MainFrame;
import interfaces.Gui;

public class SwingGui implements Gui {

    private MainFrame instance;

    @Override
    public void start() {
        instance = MainFrame.getInstance();
        instance.setVisible(true);
    }
}
