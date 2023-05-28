import gui.SwingGui;
import interfaces.ApplicationFramework;
import interfaces.Gui;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppCore {

    private static AppCore instance;

    private AppCore(){ }

    public static AppCore getInstance(){
        if (instance == null){
            instance = new AppCore();
        }
        return instance;
    }

    public static void main(String[] args) {
        Gui gui = new SwingGui();
        //interfejsi i njihove implementacije
        //trebace MessageGenerator za poruke greske (validator)

        ApplicationFramework appFramework = ApplicationFramework.getInstance();
        appFramework.initialise(gui);
        appFramework.run();
    }
}
