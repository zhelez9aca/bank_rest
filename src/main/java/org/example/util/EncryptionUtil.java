package org.example.util;

import org.example.exception.CryptoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.crypto.Cipher.DECRYPT_MODE;

@Service
public class EncryptionUtil {
    @Value("${app.pan.secret}")
    private String secret;

    public String encrypt(String pan) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            byte[] iv = new byte[12];
            new SecureRandom().nextBytes(iv);
            SecretKeySpec key = new SecretKeySpec(secret.getBytes(UTF_8), "AES");
            GCMParameterSpec spec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.ENCRYPT_MODE, key, spec);
            byte[] cipherBytes = cipher.doFinal(pan.getBytes(UTF_8));
            byte[] combined = new byte[iv.length + cipherBytes.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(cipherBytes, 0, combined, iv.length, cipherBytes.length);
            return Base64.getEncoder().encodeToString(combined);
        } catch (GeneralSecurityException ex) {
            throw new CryptoException(ex);
        }
    }

    public String decrypt(String encrypted) {
        try {
            byte[] data = Base64.getDecoder().decode(encrypted);
            byte[] iv = Arrays.copyOfRange(data, 0, 12);
            byte[] cipherBytes = Arrays.copyOfRange(data, 12, data.length);
            SecretKeySpec key = new SecretKeySpec(secret.getBytes(UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(128, iv);
            cipher.init(DECRYPT_MODE, key, spec);
            byte[] plain = cipher.doFinal(cipherBytes);
            return new String(plain, UTF_8);
        } catch (GeneralSecurityException ex) {
            throw new CryptoException(ex);
        }
    }
}
