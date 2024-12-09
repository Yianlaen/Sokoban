package view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.GameController;

public class GameWindow extends JFrame {
    private static JPanel startup, login, register, levels, game;
    private static GameController gameController = new GameController();

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
        game = new view.stages.Game(800, 600);
        showStartup();
        hideLogin();
        hideRegister();
        hideLevels();
        hideGame();
        add(startup);
        add(login);
        add(register);
        add(levels);
        add(game);
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

    public static void showGame() {
        game.setVisible(true);
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

    public static void hideGame() {
        game.setVisible(false);
    }

    public static GameController getGameController() {
        return gameController;
    }
}
