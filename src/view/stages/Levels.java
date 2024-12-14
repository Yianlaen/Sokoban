package view.stages;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.MapGenerator;
import model.Accounts;
import model.MapMatrix;
import model.RandGen;
import model.Save;
import view.GameWindow;

public class Levels extends JPanel {
    public Levels(int width, int height) {
        setSize(width, height);
        setLayout(null);

        JLabel username = new JLabel();
        username.setLocation(20, 20);
        username.setSize(200, 40);
        add(username);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent evt) {
                username
                        .setText(Accounts.getCurrentUser() == null ? "Guest User"
                                : "User: " + Accounts.getCurrentUser().getUsername());
            }
        });

        JButton backBtn = new JButton("Back");
        backBtn.setLocation(width - 150, height - 110);
        backBtn.setSize(100, 40);
        add(backBtn);
        backBtn.addActionListener(_ -> {
            GameWindow.hideLevels();
            GameWindow.showStartup();
        });

        int[][] level1 = {
                { 1, 1, 1, 1, 1, 1 },
                { 1, 8, 0, 0, 0, 1 },
                { 1, 0, 0, 4, 2, 1 },
                { 1, 0, 2, 4, 0, 1 },
                { 1, 1, 1, 1, 1, 1 }
        };

        int[][] level2 = {
                { 1, 1, 1, 1, 1, 1, 0 },
                { 1, 8, 0, 0, 0, 1, 1 },
                { 1, 0, 4, 4, 0, 0, 1 },
                { 1, 0, 1, 2, 0, 2, 1 },
                { 1, 0, 0, 0, 0, 0, 1 },
                { 1, 1, 1, 1, 1, 1, 1 }
        };

        int[][] level3 = {
                { 0, 0, 1, 1, 1, 1, 0 },
                { 1, 1, 1, 0, 0, 1, 0 },
                { 1, 8, 0, 2, 4, 1, 1 },
                { 1, 0, 0, 0, 4, 0, 1 },
                { 1, 0, 1, 2, 0, 0, 1 },
                { 1, 0, 0, 0, 0, 0, 1 },
                { 1, 1, 1, 1, 1, 1, 1 }
        };

        int[][] level4 = {
                { 0, 1, 1, 1, 1, 1, 0 },
                { 1, 1, 8, 0, 0, 1, 1 },
                { 1, 0, 0, 1, 0, 0, 1 },
                { 1, 0, 4, 6, 4, 0, 1 },
                { 1, 0, 0, 2, 0, 0, 1 },
                { 1, 1, 0, 2, 0, 1, 1 },
                { 0, 1, 1, 1, 1, 1, 0 },
        };

        int[][] level5 = {
                { 1, 1, 1, 1, 1, 1, 0, 0 },
                { 1, 0, 0, 0, 0, 1, 1, 1 },
                { 1, 0, 0, 0, 2, 2, 0, 1 },
                { 1, 0, 4, 4, 4, 8, 0, 1 },
                { 1, 0, 0, 1, 0, 2, 0, 1 },
                { 1, 1, 1, 1, 1, 1, 1, 1 },
        };

        JPanel levelsPanel = new JPanel();
        levelsPanel.setSize(400, 300);
        levelsPanel.setLocation(200, 120);
        levelsPanel.setLayout(new java.awt.GridLayout(4, 2, 50, 50));
        add(levelsPanel);

        JButton randomButton = new JButton("Random");
        levelsPanel.add(randomButton);
        randomButton.addActionListener(_ -> {
            MapGenerator nmap = new MapGenerator();
            GameWindow.getGameController().setStartMatrix(nmap.generateStart());
            GameWindow.getGameController().setLevelId(-1);
            GameWindow.hideLevels();
            GameWindow.showGame();
        });

        JButton loadButton = new JButton("Load");
        levelsPanel.add(loadButton);
        loadButton.addActionListener(_ -> {
            if (Accounts.getCurrentUser() == null) {
                JOptionPane.showMessageDialog(null, "No user logged in.");
                return;
            }
            if (!Accounts.getCurrentUser().hasSavedata()) {
                JOptionPane.showMessageDialog(null, "No savedata found.");
                return;
            }
            Save save = Accounts.getCurrentUser().getSave();
            GameWindow.getGameController().setStartMatrix(save.getSavedMap());
            GameWindow.getGameController().setLevelId(save.getSavedLevelId());
            GameWindow.getGameController().setStartSteps(save.getSavedSteps());
            GameWindow.hideLevels();
            GameWindow.showGame();
        });

        JButton level1Button = new JButton("Level 1");
        levelsPanel.add(level1Button);
        level1Button.addActionListener(_ -> {
            GameWindow.getGameController().setStartMatrix(new MapMatrix(level1));
            GameWindow.getGameController().setLevelId(1);
            GameWindow.hideLevels();
            GameWindow.showGame();
        });

        JButton level2Button = new JButton("Level 2");
        levelsPanel.add(level2Button);
        level2Button.addActionListener(_ -> {
            GameWindow.getGameController().setStartMatrix(new MapMatrix(level2));
            GameWindow.getGameController().setLevelId(2);
            GameWindow.hideLevels();
            GameWindow.showGame();
        });

        JButton level3Button = new JButton("Level 3");
        levelsPanel.add(level3Button);
        level3Button.addActionListener(_ -> {
            GameWindow.getGameController().setStartMatrix(new MapMatrix(level3));
            GameWindow.getGameController().setLevelId(3);
            GameWindow.hideLevels();
            GameWindow.showGame();
        });

        JButton level4Button = new JButton("Level 4");
        levelsPanel.add(level4Button);
        level4Button.addActionListener(_ -> {
            GameWindow.getGameController().setStartMatrix(new MapMatrix(level4));
            GameWindow.getGameController().setLevelId(4);
            GameWindow.hideLevels();
            GameWindow.showGame();
        });

        JButton level5Button = new JButton("Level 5");
        levelsPanel.add(level5Button);
        level5Button.addActionListener(_ -> {
            GameWindow.getGameController().setStartMatrix(new MapMatrix(level5));
            GameWindow.getGameController().setLevelId(5);
            GameWindow.hideLevels();
            GameWindow.showGame();
        });
    }
}
