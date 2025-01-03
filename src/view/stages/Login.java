package view.stages;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Accounts;
import view.GameWindow;

public class Login extends JPanel {
    public Login(int width, int height) {
        setSize(width, height);
        setLayout(null);

        JLabel title = new JLabel("Login");
        title.setLocation(width / 2 - 25, height / 2 - 150);
        title.setSize(100, 40);
        add(title);
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setLocation(width / 2 - 110, height / 2 - 100);
        usernameLabel.setSize(100, 40);
        add(usernameLabel);
        JTextField username = new JTextField();
        username.setLocation(width / 2 - 40, height / 2 - 95);
        username.setSize(150, 30);
        add(username);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setLocation(width / 2 - 110, height / 2 - 50);
        passwordLabel.setSize(100, 40);
        add(passwordLabel);
        JTextField password = new JTextField();
        password.setLocation(width / 2 - 40, height / 2 - 45);
        password.setSize(150, 30);
        add(password);
        JButton submitBtn = new JButton("Confirm");
        submitBtn.setLocation(width / 2 - 110, height / 2);
        submitBtn.setSize(100, 40);
        add(submitBtn);
        JButton backBtn = new JButton("Back");
        backBtn.setLocation(width / 2 + 10, height / 2);
        backBtn.setSize(100, 40);
        add(backBtn);

        submitBtn.addActionListener(_ -> {
            System.out.println("Login Attempt");
            System.out.println("U: " + username.getText());
            System.out.println("P: " + password.getText());
            switch (Accounts.login(username.getText(), password.getText())) {
                case 1:
                    Accounts.setCurrentUser(Accounts.findUserByName(username.getText()));
                    if (Accounts.getCurrentUser() == null) {
                        throw new RuntimeException("User not found");
                    }
                    username.setText("");
                    password.setText("");
                    GameWindow.hideLogin();
                    GameWindow.showLevels();
                    break;
                case 0:
                    JOptionPane.showMessageDialog(this, "Incorrect password");
                    break;
                case -1:
                    JOptionPane.showMessageDialog(this, "No such user");
                    break;
                case -2:
                    JOptionPane.showMessageDialog(this, "Invalid username");
                    break;
                case -3:
                    JOptionPane.showMessageDialog(this, "Invalid password");
                    break;
                default:
                    break;
            }
        });
        backBtn.addActionListener(_ -> {
            username.setText("");
            password.setText("");
            GameWindow.hideLogin();
            GameWindow.showStartup();
        });
    }
}
