package App;

import Views.WizardViewSwing;

import java.sql.SQLException;

public class MainSwing {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(
                () -> {
                    try {
                        new WizardViewSwing().setVisible(true);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
}
