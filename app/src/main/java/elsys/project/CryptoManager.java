package elsys.project;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyInfo;
import android.security.keystore.KeyProperties;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class CryptoManager {
    private static final String ANDROID_KEY_STORE = "AndroidKeyStore";

    public static byte[] encrypt(String alias, String plainText) {
        try {
            KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
            keyStore.load(null);

            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", ANDROID_KEY_STORE);
            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(
                    alias,
                    KeyProperties.PURPOSE_DECRYPT | KeyProperties.PURPOSE_ENCRYPT
            ).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                    .setBlockModes(KeyProperties.BLOCK_MODE_ECB)
                    .setUserAuthenticationRequired(false)
                    .setRandomizedEncryptionRequired(false);


            keyPairGenerator.initialize(builder.build());
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
            byte[] encrypted = cipher.doFinal(plainText.getBytes());
            Log.d("encryption", encrypted.length + "");
            return encrypted;
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.d("encryption", e.toString());
            return null;
        }
    }

    public static byte[] decrypt(byte[] encrypted, String alias) {
        try {
            KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
            keyStore.load(null);
            Log.d("decryption", encrypted.length + "");

            PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, null);
            KeyFactory keyFactory = KeyFactory.getInstance(privateKey.getAlgorithm(),ANDROID_KEY_STORE);
            Log.d("decryption", keyFactory.getKeySpec(privateKey, KeyInfo.class).isInsideSecureHardware() + "");
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(encrypted);
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.d("decryption", e.toString());
            return null;
        }
    }

    public static void deleteKey(String alias) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        KeyStore keyStore = KeyStore.getInstance(ANDROID_KEY_STORE);
        keyStore.load(null);

        keyStore.deleteEntry(alias);
    }

}
