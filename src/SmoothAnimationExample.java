import javax.swing.*;
import java.awt.*;

public class SmoothAnimationExample extends JPanel {
    private float alpha = 0.0f; // 不透明度

    public SmoothAnimationExample() {
        Timer timer = new Timer(50, e -> {
            alpha += 0.05f; // 增加透明度
            if (alpha > 1.0f) {
                alpha = 0.0f; // 循环
            }
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.setColor(Color.MAGENTA);
        g2d.fillOval(100, 100, 100, 100); // 绘制一个渐变透明的圆
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("平滑动画示例");
        SmoothAnimationExample animation = new SmoothAnimationExample();
        frame.add(animation);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
