package view;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameWindow extends JFrame {
    private static JPanel startup, login, register, levels;

    public GameWindow() {
        setTitle("Sokoban");
        setSize(800, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        startup = new view.stages.Startup(800, 600);
        login = new view.stages.Login(800, 600);
        register = new view.stages.Register(800, 600);
        levels = new view.stages.Levels(800, 600);
        showStartup();
        hideLogin();
        hideRegister();
        hideLevels();
        add(startup);
        add(login);
        add(register);
        add(levels);
    }

    public static void showStartup() {
        startup.setVisible(true);
    }

    public static void showLogin() {
        login.setVisible(true);
    }

    public static void showRegister() {
        register.setVisible(true);
    }

    public static void showLevels() {
        levels.setVisible(true);
    }

    public static void hideStartup() {
        startup.setVisible(false);
    }

    public static void hideLogin() {
        login.setVisible(false);
    }

    public static void hideRegister() {
        register.setVisible(false);
    }

    public static void hideLevels() {
        levels.setVisible(false);
    }
}
