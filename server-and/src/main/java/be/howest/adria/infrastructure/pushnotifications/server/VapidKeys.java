package be.howest.adria.infrastructure.pushnotifications.server;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class VapidKeys {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static KeyPairJson load(String vapidKeysPath) {
        File keysFile = new File(vapidKeysPath);
        return loadKeys(keysFile);
    }

    private static KeyPairJson loadKeys(File keysFile) {
        Gson gson = new GsonBuilder().create();
        try (FileReader reader = new FileReader(keysFile)) {
            return gson.fromJson(reader, KeyPairJson.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("VAPID keys file not found: " + keysFile.getAbsolutePath());
        }
    }

    public static class KeyPairJson {
        public final String publicKey;
        public final String privateKey;

        public KeyPairJson(String publicKey, String privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }
    }

    private VapidKeys() {
    }
}

