package view.stages;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Accounts;
import view.GameWindow;

public class Startup extends JPanel {
    public Startup(int width, int height) {
        setSize(width, height);
        setLayout(null);

        JLabel title = new JLabel("Sokoban");
        title.setLocation(width / 2 - 25, height / 2 - 150);
        title.setSize(100, 40);
        add(title);

        JButton loginButton = new JButton("Login");
        loginButton.setSize(100, 40);
        loginButton.setLocation(width / 2 - 50, height / 2 - 110);
        loginButton.addActionListener(_ -> {
            GameWindow.hideStartup();
            GameWindow.showLogin();
        });
        add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.setLocation(width / 2 - 50, height / 2 - 50);
        registerButton.setSize(100, 40);
        registerButton.addActionListener(_ -> {
            GameWindow.hideStartup();
            GameWindow.showRegister();
        });
        add(registerButton);

        JButton guestButton = new JButton("Guest");
        guestButton.setLocation(width / 2 - 50, height / 2 + 10);
        guestButton.setSize(100, 40);
        guestButton.addActionListener(_ -> {
            Accounts.setCurrentUser(null);
            GameWindow.hideStartup();
            GameWindow.showLevels();
        });
        guestButton.setVisible(true);
        add(guestButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setLocation(width / 2 - 50, height / 2 + 70);
        exitButton.setSize(100, 40);
        exitButton.addActionListener(_ -> System.exit(0));
        exitButton.setVisible(true);
        add(exitButton);

        JLabel specialThanks = new JLabel("External Code Used: https://github.com/pajouheshgar/3D-Swing");
        specialThanks.setLocation(width / 2 - 200, height - 50);
        specialThanks.setSize(400, 40);
        add(specialThanks);
    }
}
