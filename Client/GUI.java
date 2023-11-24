
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

    Cue cue = new Cue();

    public GUI() {
        this.balls = new ArrayList<CBall>();

        setSize(CConstants.tableWidth + CConstants.widthOffset * 2,
                CConstants.tableHeight + CConstants.heightOffset * 2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the button and text field
        JPanel panel = new JPanel();

        panel.add(ballTypeLabel);
        panel.add(waitLabel);
        panel.add(endGameLabel);

        // Add the panel to the frame
        add(panel, BorderLayout.SOUTH);

        setVisible(true);

        // Load the images in the array
        for (int i = 0; i < 16; i++) {
            try {
                File fileBall = new File("./pool_assets/ball" + i + ".png");
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
            cueImage = ImageIO.read(fileCue).getScaledInstance(CConstants.cueWidth,
                    CConstants.cueHeight,
                    Image.SCALE_DEFAULT);
            ;
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create the Cue object and add the key listener

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (!isturn) {
                    return;
                }

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        cue.setAngle(cue.getAngle() - 0.05);
                        break;
                    case KeyEvent.VK_RIGHT:
                        cue.setAngle(cue.getAngle() + 0.05);
                        break;
                    case KeyEvent.VK_UP:
                        cue.setPower(Math.min(cue.getPower() + 0.5, 10));
                        break;
                    case KeyEvent.VK_DOWN:
                        cue.setPower(Math.max(cue.getPower() - 0.5, 1));
                        break;
                    case KeyEvent.VK_ENTER:
                        message = (cue.getAngle() - Math.PI) + ";" + cue.getPower();
                        isturn = false;
                        break;
                }
                repaint();
            }
        });

        setVisible(true);
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

        if (isturn) {
            // rotation coordinates
            double distance = (10 * cue.getPower()) + CConstants.getRadius();

            int x1 = (int) Math.round(balls.get(0).x + distance * Math.cos(cue.getAngle()));
            int y1 = (int) Math.round(balls.get(0).y + distance * Math.sin(cue.getAngle()));

            // g.drawImage(rotate(cueImage, cue.getAngle()), x + CConstants.widthOffset, y +
            // CConstants.heightOffset /* - CConstants.cueHeight / 2 */,
            // null);

            int x2 = (int) Math.round(x1 + CConstants.cueWidth * Math.cos(cue.getAngle()));
            int y2 = (int) Math.round(y1 + CConstants.cueWidth * Math.sin(cue.getAngle()));

            g.setColor(Color.BLACK);
            g.drawLine(x1 + CConstants.widthOffset, y1 + CConstants.heightOffset, x2 + CConstants.widthOffset,
                    y2 + CConstants.heightOffset);
        }

        updateBalls(balls);
    }

    public BufferedImage rotate(Image img, Double angle) {

        BufferedImage bimg = toBufferedImage(img);

        double sin = Math.abs(Math.sin(angle)),
                cos = Math.abs(Math.cos(angle));
        int w = bimg.getWidth();
        int h = bimg.getHeight();
        int neww = (int) Math.floor(w * cos + h * sin),
                newh = (int) Math.floor(h * cos + w * sin);
        BufferedImage rotated = new BufferedImage(neww, newh, bimg.getType());
        Graphics2D graphic = rotated.createGraphics();

        graphic.translate((neww - w) / 2, (newh - h) / 2);

        graphic.rotate(angle, w / 2, h / 2);
        graphic.drawRenderedImage(bimg, null);
        graphic.dispose();
        return rotated;
    }

    public BufferedImage toBufferedImage(Image img) {

        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.drawImage(img, 0, 0, null);
        graphics2D.dispose();

        return bufferedImage;
    }

    public JButton getButton() {
        return button;
    }

    public String getPlayerMove() {
        return textField.getText();
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