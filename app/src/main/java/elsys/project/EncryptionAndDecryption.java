package elsys.project;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class EncryptionAndDecryption {
    public static String encrypt(String text, String secretKeyAlias) throws Exception{
        try {
            generateKeyIfNotAvailable(secretKeyAlias);

            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            SecretKey secretKey = (SecretKey) keyStore.getKey(secretKeyAlias, null);

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedData = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));

            byte[] iv = new byte[12];
            Random random = new Random();
            random.nextBytes(iv);
            return new String(iv) + "\n" + encryptedData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String encryptedText, String secretKeyAlias) {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            SecretKey secretKey = (SecretKey) keyStore.getKey(secretKeyAlias, null);

            String[] lines = encryptedText.split("\n");

            Log.d("decryption", lines[0]);

            byte[] iv = lines[0].getBytes();

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(128, iv));

            keyStore.deleteEntry(secretKeyAlias);

            return new String(cipher.doFinal(lines[1].getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            Log.d("decryption", e.toString());
            e.printStackTrace();
            return null;
        }
    }

    private static void generateKeyIfNotAvailable(String secretKeyAlias) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);

        // Check if key exists, if not, generate a new one
        if (!keyStore.containsAlias(secretKeyAlias)) {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(
                    secretKeyAlias,
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT);

            builder.setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE);

            keyGenerator.init(builder.build());
            keyGenerator.generateKey();
        }
    }
}
