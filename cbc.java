import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Scanner;

public class cbc {
    public static void main(String[] args) throws Exception {
        // 输入明文、密钥和初始向量
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter plaintext: ");
        String plaintext = scanner.nextLine();
        System.out.print("Enter encryption key : ");
        String key = scanner.nextLine();
        System.out.print("Enter IV : ");
        String iv = scanner.nextLine();

        // 加密
        byte[] ciphertext = encrypt(plaintext, key, iv);

        // 输出密文
        String encodedCiphertext = Base64.getEncoder().encodeToString(ciphertext);
        System.out.println("Ciphertext: " + encodedCiphertext);
        
        // 模拟篡改密文
        System.out.print("Enter the index of the byte to modify in the ciphertext: ");
        int index = scanner.nextInt();
        ciphertext[index] = (byte) (ciphertext[index] ^ 0x01);
        
        // 解密
        String decryptedText = decrypt(ciphertext, key, iv);
        System.out.println("Decrypted Text: " +decryptedText );
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
}