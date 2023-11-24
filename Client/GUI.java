
// GUI.java
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import java.awt.event.ActionEvent;

public class GUI extends JFrame {
    private List<CBall> balls;
    private JButton button;
    private JTextField textField;

    private Image offScreenImageDrawed;
    private Graphics offScreenGraphicsDrawed;

    public Image[] ballImages = new Image[16];

    private JLabel ballTypeLabel = new JLabel();
    private JLabel waitLabel = new JLabel();
    private JLabel endGameLabel = new JLabel();

    private Image cueImage;

    public Boolean isturn = false;
    public String message;

    public GUI() {
        this.balls = new ArrayList<CBall>();

        setSize(CConstants.tableWidth + CConstants.widthOffset * 2,
                CConstants.tableHeight + CConstants.heightOffset * 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the button and text field
        JPanel panel = new JPanel();

        // Add the panel to the frame
        add(panel, BorderLayout.SOUTH);

        setVisible(true);

        // Load the images in the array
        for (int i = 0; i < 16; i++) {
            try {
                File fileBall = new File("pool_assets/ball" + i + ".png");
                System.out.println(fileBall.getAbsolutePath());
                ballImages[i] = ImageIO.read(fileBall).getScaledInstance(CConstants.ballDiameter,
                        CConstants.ballDiameter,
                        Image.SCALE_DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Initialize cueImage with the cue stick image
        try {
            File fileCue = new File("./pool_assets/cue.png");
            // System.out.println(file.getAbsolutePath());
            cueImage = ImageIO.read(fileCue);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create the Cue object and add the key listener
        Cue cue = new Cue();
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (!isturn) {
                    return;
                }

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        cue.setAngle(cue.getAngle() - 1);
                        break;
                    case KeyEvent.VK_RIGHT:
                        cue.setAngle(cue.getAngle() + 1);
                        break;
                    case KeyEvent.VK_UP:
                        cue.setPower(Math.min(cue.getPower() + 1, 5));
                        break;
                    case KeyEvent.VK_DOWN:
                        cue.setPower(Math.max(cue.getPower() - 1, 1));
                        break;
                    case KeyEvent.VK_ENTER:
                        message = cue.getAngle() + ";" + cue.getPower();
                        setMessage(message);
                        isturn = false;
                        break;
                }
                updateCue(cue);
            }
        });

        setVisible(true);
    }

    public void updateCue(Cue cue) {
        // Define the power scale factor
        final double POWER_SCALE_FACTOR = 5.0;

        // Calculate the new size based on the power
        int newSize = (int) (cueImage.getWidth(null) * cue.getPower() / POWER_SCALE_FACTOR);

        // Scale the image
        Image scaledCueImage = cueImage.getScaledInstance(newSize, newSize, Image.SCALE_DEFAULT);

        // Create a new image to draw the rotated cue onto
        BufferedImage rotatedCueImage = new BufferedImage(newSize, newSize, BufferedImage.TYPE_INT_ARGB);

        // Get the graphics context from the new image
        Graphics2D g2d = rotatedCueImage.createGraphics();

        // Rotate the image
        g2d.rotate(Math.toRadians(cue.getAngle()), newSize / 2, newSize / 2);
        g2d.drawImage(scaledCueImage, 0, 0, null);
        g2d.dispose();

        // Set the cue image to the new rotated image
        cueImage = rotatedCueImage;

        // Repaint the GUI to show the updated cue
        repaint();
    }

    public void showBallTypeLabelFor10Sec() {
        ballTypeLabel.setVisible(true);

        Timer timer = new Timer(10000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ballTypeLabel.setVisible(false);
            }
        });

        timer.setRepeats(false);
        timer.start();
    }

    public void updateBalls(List<CBall> balls) {
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
        g.fillRect(CConstants.widthOffset, CConstants.heightOffset, CConstants.tableWidth, CConstants.tableHeight);

        g.setColor(Color.black);

        g.fillOval(CConstants.widthOffset - CConstants.potDiameter / 2,
                CConstants.heightOffset - CConstants.potDiameter / 2, CConstants.potDiameter, CConstants.potDiameter);
        g.fillOval(CConstants.widthOffset + (CConstants.tableWidth / 2 - CConstants.potDiameter / 2),
                CConstants.heightOffset - CConstants.potDiameter / 2, CConstants.potDiameter, CConstants.potDiameter);
        g.fillOval(CConstants.widthOffset + (CConstants.tableWidth - CConstants.potDiameter / 2),
                CConstants.heightOffset - CConstants.potDiameter / 2, CConstants.potDiameter, CConstants.potDiameter);

        g.fillOval(CConstants.widthOffset - CConstants.potDiameter / 2,
                CConstants.heightOffset + (CConstants.tableHeight - CConstants.potDiameter / 2), CConstants.potDiameter,
                CConstants.potDiameter);
        g.fillOval(CConstants.widthOffset + (CConstants.tableWidth / 2 - CConstants.potDiameter / 2),
                CConstants.heightOffset + (CConstants.tableHeight - CConstants.potDiameter / 2), CConstants.potDiameter,
                CConstants.potDiameter);
        g.fillOval(CConstants.widthOffset + (CConstants.tableWidth - CConstants.potDiameter / 2),
                CConstants.heightOffset + (CConstants.tableHeight - CConstants.potDiameter / 2), CConstants.potDiameter,
                CConstants.potDiameter);

        // Draw the balls using the images
        for (CBall ball : balls) {

            if (ball.getX() >= 0 && ball.getY() >= 0)
                g.drawImage(ballImages[ball.getNumber()], ball.getX() + CConstants.widthOffset - CConstants.getRadius(),
                        ball.getY() + CConstants.heightOffset - CConstants.getRadius(), null);
        }

        showBallTypeLabelFor10Sec();

        updateBalls(balls);

    }

    public JButton getButton() {
        return button;
    }

    public String getPlayerMove() {
        return textField.getText();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void updateBallType(String ballType) {
        switch (ballType) {
            case "null":
                ballTypeLabel.setText("");
                break;
            case "full":
                ballTypeLabel.setText("Devi colpire una palla piena.");
                break;
            case "half":
                ballTypeLabel.setText("Devi colpire una palla mezza.");
                break;
        }
    }

    public void showWaitLabel() {
        waitLabel.setVisible(true);
    }

    public void hideWaitLabel() {
        waitLabel.setVisible(false);
    }

    public void showEndGameLabel(boolean hasWon) {
        if (hasWon) {
            endGameLabel.setText("Hai vinto!");
        } else {
            endGameLabel.setText("Hai perso!");
        }
        endGameLabel.setVisible(true);
    }

    public void hideEndGameLabel() {
        endGameLabel.setVisible(false);
    }
}