import javax.swing.SwingUtilities;

import model.Accounts;
import view.GameWindow;

public class App {
    public static void main(String[] args) throws Exception {
        java.util.Locale.setDefault(java.util.Locale.ENGLISH);
        System.out.println("Sokoban project started!");
        Accounts.load();
        SwingUtilities.invokeLater(() -> {
            new GameWindow();
        });
    }
}
