package view;

import javax.swing.JFrame;

import controller.GameController;

public class GameWindow extends JFrame {
    private static view.stages.Startup startup;
    private static view.stages.Login login;
    private static view.stages.Register register;
    private static view.stages.Levels levels;
    private static view.stages.Game game;
    private static GameController gameController;

    public GameWindow() {
        setTitle("Sokoban");
        setSize(800, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        int width = (int) getContentPane().getSize().getWidth(), height = (int) getContentPane().getSize().getHeight();

        startup = new view.stages.Startup(width, height);
        login = new view.stages.Login(width, height);
        register = new view.stages.Register(width, height);
        levels = new view.stages.Levels(width, height);
        game = new view.stages.Game(width, height);
        gameController = new GameController();
        game.setGameController(gameController);
        gameController.setPanel(game);
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
