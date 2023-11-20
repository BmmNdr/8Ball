
// GUI.java
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame {
    private List<Ball> balls;
    private JButton button;
    private JTextField textField;

    private Image offScreenImageDrawed;
    private Graphics offScreenGraphicsDrawed;

    public Image[] ballImages = new Image[16];

    public GUI() {
        this.balls = new ArrayList<Ball>();

        setSize(Constants.tableWidth + Constants.widthOffset * 2, Constants.tableHeight + Constants.heightOffset * 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the button and text field
        JPanel panel = new JPanel();

        // Add the panel to the frame
        add(panel, BorderLayout.SOUTH);

        setVisible(true);

        // Load the images in the array
        for (int i = 0; i < 16; i++) {
            try {
                File file = new File("./pool_assets/ball" + i + ".png");
                // System.out.println(file.getAbsolutePath());
                ballImages[i] = ImageIO.read(file).getScaledInstance(Constants.ballDiameter, Constants.ballDiameter, Image.SCALE_DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateBalls(List<Ball> balls) {
        this.balls = balls;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        final Dimension d = getSize();
        offScreenImageDrawed = createImage(d.width, d.height);

        this.offScreenGraphicsDrawed = offScreenImageDrawed.getGraphics();
        this.offScreenGraphicsDrawed.setColor(Color.white);
        this.offScreenGraphicsDrawed.fillRect(0, 0, d.width, d.height);
        /////////////////////
        // Paint Offscreen //
        /////////////////////
        renderOffScreen(offScreenImageDrawed.getGraphics());
        g.drawImage(offScreenImageDrawed, 0, 0, null);
    }

    public void renderOffScreen(Graphics g) {
        super.paint(g);

        // Draw the pool table
        g.setColor(new Color(0, 85, 54));
        g.fillRect(Constants.widthOffset, Constants.heightOffset, Constants.tableWidth, Constants.tableHeight);

        g.setColor(Color.black);

        g.fillOval(Constants.widthOffset - Constants.potDiameter / 2, Constants.heightOffset - Constants.potDiameter / 2, Constants.potDiameter, Constants.potDiameter);
        g.fillOval(Constants.widthOffset + (Constants.tableWidth / 2 - Constants.potDiameter / 2), Constants.heightOffset - Constants.potDiameter / 2, Constants.potDiameter, Constants.potDiameter);
        g.fillOval(Constants.widthOffset + (Constants.tableWidth - Constants.potDiameter / 2), Constants.heightOffset - Constants.potDiameter / 2, Constants.potDiameter, Constants.potDiameter);

        g.fillOval(Constants.widthOffset - Constants.potDiameter / 2, Constants.heightOffset + (Constants.tableHeight - Constants.potDiameter / 2), Constants.potDiameter, Constants.potDiameter);
        g.fillOval(Constants.widthOffset + (Constants.tableWidth / 2 - Constants.potDiameter / 2), Constants.heightOffset + (Constants.tableHeight - Constants.potDiameter / 2), Constants.potDiameter, Constants.potDiameter);
        g.fillOval(Constants.widthOffset + (Constants.tableWidth - Constants.potDiameter / 2), Constants.heightOffset + (Constants.tableHeight - Constants.potDiameter / 2), Constants.potDiameter, Constants.potDiameter);

        // Draw the balls using the images
        for (Ball ball : balls) {

            if(ball.getX() >= 0 && ball.getY() >= 0)
                g.drawImage(ballImages[ball.getNumber()], ball.getX() + Constants.widthOffset - Constants.getRadius(), ball.getY() + Constants.heightOffset - Constants.getRadius(), null);
        }

        updateBalls(balls);

    }

    public JButton getButton() {
        return button;
    }

    public String getPlayerMove() {
        return textField.getText();
    }
}