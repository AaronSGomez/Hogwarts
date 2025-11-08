package App;

import Controllers.WizardController;
import Models.*;
import Views.WizardView;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        WizardView wizardView = new WizardView();

        wizardView.showMenu();




    }
}
