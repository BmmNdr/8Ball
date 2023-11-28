import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;

/**
 * The ServerConnectionWindow class represents a graphical user interface window for connecting to a server.
 * It allows the user to enter an IP address and port number, and then attempts to establish a connection to the server.
 */
public class ServerConnectionWindow {

    public String ipAddress;
    public int port;
    public JFrame frame;

    /**
     * Checks if the given IP address is valid.
     * 
     * @param ip the IP address to be validated
     * @return true if the IP address is valid, false otherwise
     */
    private static boolean isValidIP(String ip) {
        try {
            if (ip == null || ip.isEmpty()) {
                return false;
            }

            String[] parts = ip.split("\\.");
            if (parts.length != 4) {
                return false;
            }

            for (String part : parts) {
                int num = Integer.parseInt(part);
                if (num < 0 || num > 255) {
                    return false;
                }
            }

            if (ip.endsWith(".")) {
                return false;
            }

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Pings the server at the specified IP address to check if it is reachable.
     * 
     * @param ipAddress the IP address of the server
     * @return true if the server is reachable, false otherwise
     */
    private static boolean pingServer(String ipAddress) {
        try {
            InetAddress address = InetAddress.getByName(ipAddress);
            if (address.isReachable(5000)) {
                System.out.println("Ping successful!");
                return true;
            } else {
                System.out.println("Ping failed.");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Error during ping: " + e.getMessage());
            return false;
        }
    }

    
    public ServerConnectionWindow() {
        frame = new JFrame("Server Connection");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel ipLabel = new JLabel("Enter IP Address:");
        ipLabel.setBounds(20, 20, 120, 25);
        frame.add(ipLabel);

        JTextField ipField = new JTextField();
        ipField.setText("127.0.0.1");
        ipField.setBounds(150, 20, 120, 25);
        frame.add(ipField);

        JLabel portLabel = new JLabel("Enter Port:");
        portLabel.setBounds(20, 50, 120, 25);
        frame.add(portLabel);

        JTextField portField = new JTextField();
        portField.setText("666");
        portField.setBounds(150, 50, 120, 25);
        frame.add(portField);

        JButton connectButton = new JButton("Connect");
        connectButton.setBounds(100, 130, 100, 25);

        connectButton.addActionListener(e -> {

            ipAddress = ipField.getText();
            String portText = portField.getText();

            if (ipAddress.isEmpty() || portText.isEmpty() || !portText.matches("[0-9]+"))
                return;

            port = Integer.parseInt(portText);

            if (isValidIP(ipAddress)) {
                if (pingServer(ipAddress)) {
                    System.out.println("Connecting to " + ipAddress + " on port " + port);

                    // Closing the window after connecting
                    frame.setVisible(false);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Ping to the server failed. Please check the IP address.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter a valid IP address.");
            }
        });
        frame.add(connectButton);

        frame.setVisible(true);
    }
}
