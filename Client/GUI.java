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


        setSize(800, 400);
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


        updateBalls(balls);
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
        g.fillRect(0, 0, 800, 400);

        // Draw the balls
        for (Ball ball : balls) {
            switch (ball.number) {
                case 0: g.setColor(Color.WHITE); break;
                case 1: g.setColor(Color.YELLOW); break;
                case 2: g.setColor(Color.BLUE); break;
                case 3: g.setColor(Color.RED); break;
                case 4: g.setColor(Color.MAGENTA); break;
                case 5: g.setColor(Color.ORANGE); break;
                case 6: g.setColor(Color.GREEN); break;
                case 7: g.setColor(Color.GRAY); break;
                case 8: g.setColor(Color.BLACK); break;
                case 9: g.setColor(Color.YELLOW); break;
                case 10: g.setColor(Color.BLUE); break;
                case 11: g.setColor(Color.RED); break;
                case 12: g.setColor(Color.MAGENTA); break;
                case 13: g.setColor(Color.ORANGE); break;
                case 14: g.setColor(Color.GREEN); break;
                case 15: g.setColor(Color.GRAY); break;
                default: g.setColor(Color.BLACK); break;
            }
            g.fillOval(ball.x - 13, ball.y - 13, 26, 26);

            if(ball.number != 0 && ball.number % 2 == 0){ //TODO divide as in real life
                g.setColor(Color.WHITE);

                g.fillOval(ball.x - 7, ball.y - 7, 13, 13);
            }
        }
    }

    public JButton getButton() {
        return button;
    }

    public String getPlayerMove() {
        return textField.getText();
    }
}   