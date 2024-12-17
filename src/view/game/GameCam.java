package view.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.Timer;

import Swing3D.AnimatedCharacter3D;
import Swing3D.AnimatedCharacter3DFactory;
import Swing3D.Camera;
import Swing3D.Object3D;
import Swing3D.Object3DFactory;
import Swing3D.PointLight;
import Swing3D.Projection;
import Swing3D.Vector2;
import Swing3D.Vector3;
import Swing3D.ZBuffer;

public class GameCam extends JPanel {
    private Object3D[][] grids;
    private AnimatedCharacter3D[][] boxes;
    private AnimatedCharacter3D hero;
    private Camera camera;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = ((Graphics2D) g);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getCols(); j++) {
                if (grids[i][j] != null)
                    grids[i][j].render(g2d);
                if (boxes[i][j] != null)
                    boxes[i][j].render(g2d);
            }
        }
        if (hero != null)
            hero.render(g2d);
        ZBuffer.renderAll(g2d);
    }

    private void placeCamera() {
        camera.moveTo(hero.getCharacter3D().getCenter().plus(new Vector3(5, 0, 7)));
        camera.lookAt(hero.getCharacter3D().getCenter());
        camera.setUpVector(new Vector3(0, 0, 1));
    }

    public int getRows() {
        return grids.length;
    }

    public int getCols() {
        return grids[0].length;
    }

    public GameCam(int rows, int cols) {
        setSize(600, 600);
        setLayout(null);
        setBackground(Color.PINK);
        grids = new Object3D[rows][cols];
        boxes = new AnimatedCharacter3D[rows][cols];
        PointLight pointLight = new PointLight(new Vector3(rows, cols, 30), 0.3f, Color.WHITE);
        Projection.setPointLight(pointLight);
        camera = new Camera(new Vector3(0, 0, 1), new Vector3(-1, 0, 0), new Vector3(0, 0, 0), 1);
        Projection.setCamera(camera);
        Timer timer = new Timer(15, _ -> repaint());
        timer.start();
    }

    public void paintGrid(int i, int j, int type) {
        System.out.println("Painting grid " + i + " " + j + " " + type);
        switch (type) {
            case 0: // Ground
                grids[i][j] = Object3DFactory.createGround(new Vector2(i, j), 1, 1, Color.WHITE);
                break;
            case 1: // Wall
                grids[i][j] = Object3DFactory.createNonSymmetricCube(1, 1, 1, Color.GRAY)
                        .translate(new Vector3(i, j, 0));
                break;
            case 2: // Goal
                grids[i][j] = Object3DFactory.createGround(new Vector2(i, j), 1, 1, Color.GREEN);
                break;
            default:
                throw new IllegalArgumentException("Invalid grid type");
        }
    }

    public void setBoxInGrid(int i, int j) {
        if (boxes[i][j] != null) {
            throw new RuntimeException("Box already exists in this grid");
        }
        boxes[i][j] = AnimatedCharacter3DFactory.createAnimatedBuilding(0.5f, Color.ORANGE);
        boxes[i][j].translate(new Vector3(i, j, 0));
    }

    public void setHeroInGrid(int i, int j) {
        hero = AnimatedCharacter3DFactory.createAnimatedBuilding(0.5f, Color.BLUE);
        hero.translate(new Vector3(i, j, 0));
        placeCamera();
    }

    public void moveObj(AnimatedCharacter3D obj, int dRow, int dCol) {
        final int[] count = { 0 };
        Timer timer = new Timer(15, e -> {
            if (count[0] < 10) {
                obj.translate(new Vector3(dRow * 0.1f, dCol * 0.1f, 0));
                placeCamera();
                count[0]++;
            } else {
                ((Timer) e.getSource()).stop();
            }
        });
        timer.start();
    }

    public void moveHero(int row, int col, int dRow, int dCol) {
        moveObj(hero, dRow, dCol);
    }

    public void moveHeroBox(int row, int col, int dRow, int dCol) {
        AnimatedCharacter3D box = boxes[row + dRow][col + dCol];
        moveObj(hero, dRow, dCol);
        moveObj(box, dRow, dCol);
        boxes[row + 2 * dRow][col + 2 * dCol] = box;
        boxes[row + dRow][col + dCol] = null;
    }
}
