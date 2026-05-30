package com.carlos.tfm.therapy.Security.Service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
public class EncryptionService {

    @Value("${app.encryption.key}")
    private String secret;

    private SecretKeySpec secretKey;

    @PostConstruct
    public void init() {
        secretKey = new SecretKeySpec(secret.getBytes(), "AES");
    }

    public String encrypt(String value) {

        try {

            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(value.getBytes()));

        } catch (Exception e) {
            throw new RuntimeException("Error encrypting text");
        }
    }

    public String decrypt(String value) {

        try {

            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            return new String(
                    cipher.doFinal(
                            Base64.getDecoder().decode(value)
                    )
            );

        } catch (Exception e) {
            throw new RuntimeException("Error decrypting text");
        }
    }
}

