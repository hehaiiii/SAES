import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Base64;

import javax.swing.*;

public class SAESCBCDemoGUI extends JFrame {
    private JTextArea inputText;
    private JTextArea outputText;
    private JTextField keyField;
    private JTextField ivField;

    public SAESCBCDemoGUI() {
        setTitle("AES CBC Encryption Demo");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        add(panel);

        panel.add(new JLabel("Input Text:"));
        inputText = new JTextArea();
        panel.add(inputText);

        panel.add(new JLabel("Encryption Key (16 bytes):"));
        keyField = new JTextField();
        panel.add(keyField);

        panel.add(new JLabel("IV (16 bytes):"));
        ivField = new JTextField();
        panel.add(ivField);

        JButton encryptButton = new JButton("Encrypt");
        panel.add(encryptButton);

        JButton decryptButton = new JButton("Decrypt");
        panel.add(decryptButton);

        outputText = new JTextArea();
        outputText.setEditable(false);
        panel.add(new JScrollPane(outputText));

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String plaintext = inputText.getText();
                    String key = keyField.getText();
                    String iv = ivField.getText();
                    byte[] ciphertext = encrypt(plaintext, key, iv);
                    String encodedCiphertext = Base64.getEncoder().encodeToString(ciphertext);
                    outputText.setText("Ciphertext: " + encodedCiphertext);
                } catch (Exception ex) {
                    outputText.setText("Error: " + ex.getMessage());
                }
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String encodedCiphertext = inputText.getText();
                    byte[] ciphertext = Base64.getDecoder().decode(encodedCiphertext);
                    String key = keyField.getText();
                    String iv = ivField.getText();
                    String decryptedText = decrypt(ciphertext, key, iv);
                    outputText.setText("Decrypted Text: " + decryptedText);
                } catch (Exception ex) {
                    outputText.setText("Error: " + ex.getMessage());
                }
            }
        });
    }

    public static byte[] encrypt(String plaintext, String key, String iv) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        return cipher.doFinal(plaintext.getBytes());
    }

    public static String decrypt(byte[] ciphertext, String key, String iv) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        byte[] decryptedBytes = cipher.doFinal(ciphertext);
        return new String(decryptedBytes);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SAESCBCDemoGUI gui = new SAESCBCDemoGUI();
                gui.setVisible(true);
            }
        });
    }
}