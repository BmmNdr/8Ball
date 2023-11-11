// GUI.java
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame {
    private List<Ball> balls;
    private JButton button;
    private JTextField textField;

    public GUI() {
        this.balls = new ArrayList<Ball>();


        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the button and text field
        button = new JButton("Submit Move");
        textField = new JTextField(20);
        JPanel panel = new JPanel();
        panel.add(textField);
        panel.add(button);

        // Add the panel to the frame
        add(panel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void updateBalls(List<Ball> balls) {
        this.balls = balls;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Draw the pool table
        g.setColor(new Color(0, 85, 54));
        g.fillRect(50, 50, 700, 500);

        // Draw the balls
        for (Ball ball : balls) {
            switch (ball.number) {
                case 1: g.setColor(Color.YELLOW); break;
                // ... other cases ...
                default: g.setColor(Color.BLACK); break;
            }
            g.fillOval(ball.x, ball.y, 10, 10);
        }
    }

    public JButton getButton() {
        return button;
    }

    public String getPlayerMove() {
        return textField.getText();
    }
}   