import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("S-AES Encryption");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel dataLabel = new JLabel("Enter 16-bit Data:");
        JTextField dataField = new JTextField(16);

        JLabel keyLabel = new JLabel("Enter 16-bit Key:");
        JTextField keyField = new JTextField(32);

        JButton encryptButton = new JButton("Encrypt");
        JButton doubleencryptButton = new JButton("Double-Encrypt");
        JButton decryptButton = new JButton("Decrypt");
        JButton doubledecryptButton = new JButton("Double-Decrypt");
        JButton TribleDecryptButton = new JButton("Trible-Decrypt");
        JButton TribleEncryptButton = new JButton("Trible-Encrypt");
        JTextField resultArea = new JTextField(40);

        panel.add(dataLabel);
        panel.add(dataField);
        panel.add(keyLabel);
        panel.add(keyField);
        panel.add(encryptButton);
        panel.add(decryptButton);
        panel.add(doubleencryptButton);
        panel.add(doubledecryptButton);
        panel.add(TribleDecryptButton);
        panel.add(TribleEncryptButton);
        encryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String data = dataField.getText();
                String key = keyField.getText();
                int[][] plaintext = Ssaes.StringtoIntArray28(data);
                int[][] intkey = Ssaes.StringtoIntArray28(key);
                int[][] ciphertext = Ssaes.Encrypt(plaintext, intkey);

                resultArea.setText("Encrypted Data: " + Ssaes.IntArrayToString(ciphertext));
            }
        });
        decryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String data = dataField.getText();
                String key = keyField.getText();
                int[][] plaintext = Ssaes.StringtoIntArray28(data);
                int[][] intkey = Ssaes.StringtoIntArray28(key);
                int[][] ciphertext = Ssaes.Decrypt(plaintext, intkey);

                resultArea.setText("Decrypted Data: " + Ssaes.IntArrayToString(ciphertext));
            }
        });
        doubleencryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String data = dataField.getText();
                String key = keyField.getText();
                int mid = key.length() / 2;
                String key1 = key.substring(0, mid);
                String key2 = key.substring(mid);
                int[][] plaintext = Ssaes.StringtoIntArray28(data);
                int[][] intkey1 = Ssaes.StringtoIntArray28(key1);
                int[][] intkey2 = Ssaes.StringtoIntArray28(key2);
                int[][] ciphertext1 = Ssaes.Encrypt(plaintext, intkey1);
                int[][] ciphertext2 = Ssaes.Encrypt(ciphertext1, intkey2);
                resultArea.setText("DoubleEncrypted Data: " + Ssaes.IntArrayToString(ciphertext2));
            }
        });
        doubledecryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String data = dataField.getText();
                String key = keyField.getText();
                int mid = key.length() / 2;
                String key1 = key.substring(0, mid);
                String key2 = key.substring(mid);
                int[][] plaintext = Ssaes.StringtoIntArray28(data);
                int[][] intkey1 = Ssaes.StringtoIntArray28(key1);
                int[][] intkey2 = Ssaes.StringtoIntArray28(key2);
                int[][] ciphertext1 = Ssaes.Decrypt(plaintext, intkey2);
                int[][] ciphertext2 = Ssaes.Decrypt(ciphertext1, intkey1);
                resultArea.setText("DoubleDecrypted Data: " + Ssaes.IntArrayToString(ciphertext2));
            }
        });
        TribleEncryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String data = dataField.getText();
                String key = keyField.getText();
                int mid = key.length() / 2;
                String key1 = key.substring(0, mid);
                String key2 = key.substring(mid);
                int[][] plaintext = Ssaes.StringtoIntArray28(data);
                int[][] intkey1 = Ssaes.StringtoIntArray28(key1);
                int[][] intkey2 = Ssaes.StringtoIntArray28(key2);
                int[][] ciphertext1 = Ssaes.Encrypt(plaintext, intkey1);
                int[][] ciphertext2 = Ssaes.Decrypt(ciphertext1, intkey2);
                int[][] ciphertext3 = Ssaes.Encrypt(ciphertext2, intkey1);
                resultArea.setText("TribleEncrypted Data: " + Ssaes.IntArrayToString(ciphertext3));
            }
        });
        TribleDecryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String data = dataField.getText();
                String key = keyField.getText();
                int mid = key.length() / 2;
                String key1 = key.substring(0, mid);
                String key2 = key.substring(mid);
                int[][] plaintext = Ssaes.StringtoIntArray28(data);
                int[][] intkey1 = Ssaes.StringtoIntArray28(key1);
                int[][] intkey2 = Ssaes.StringtoIntArray28(key2);
                int[][] ciphertext1 = Ssaes.Decrypt(plaintext, intkey1);
                int[][] ciphertext2 = Ssaes.Encrypt(ciphertext1, intkey2);
                int[][] ciphertext3 = Ssaes.Decrypt(ciphertext2, intkey1);
                resultArea.setText("TribleDecrypted Data: " + Ssaes.IntArrayToString(ciphertext3));
            }
        });TribleDecryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String data = dataField.getText();
                String key = keyField.getText();
                int mid = key.length() / 2;
                String key1 = key.substring(0, mid);
                String key2 = key.substring(mid);
                int[][] plaintext = Ssaes.StringtoIntArray28(data);
                int[][] intkey1 = Ssaes.StringtoIntArray28(key1);
                int[][] intkey2 = Ssaes.StringtoIntArray28(key2);
                int[][] ciphertext1 = Ssaes.Decrypt(plaintext, intkey1);
                int[][] ciphertext2 = Ssaes.Encrypt(ciphertext1, intkey2);
                int[][] ciphertext3 = Ssaes.Decrypt(ciphertext2, intkey1);
                resultArea.setText("TribleDecrypted Data: " + Ssaes.IntArrayToString(ciphertext3));
            }
        });


        panel.add(resultArea);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}