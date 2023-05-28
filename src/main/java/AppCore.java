import database.DBController;
import database.MongoDB;
import gui.SwingGui;
import interfaces.ApplicationFramework;
import interfaces.Database;
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
        Database db = new DBController();
        //interfejsi i njihove implementacije
        //trebace MessageGenerator za poruke greske (validator)

        ApplicationFramework appFramework = ApplicationFramework.getInstance();
        appFramework.initialise(gui, db);
        appFramework.run();

        db.preformQuery("db.employees.find({})");
    }
}
