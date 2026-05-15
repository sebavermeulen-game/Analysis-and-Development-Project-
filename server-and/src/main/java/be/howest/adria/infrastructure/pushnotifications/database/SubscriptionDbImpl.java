package be.howest.adria.infrastructure.pushnotifications.database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.gson.Gson;

import nl.martijndwars.webpush.Subscription;


public class SubscriptionDbImpl implements SubscriptionDb {
    private static final Logger LOGGER = Logger.getLogger(SubscriptionDbImpl.class.getName());
    private final String dbPath;
    private final Gson gson;

    public SubscriptionDbImpl(String dbPath) {
        this.dbPath = dbPath;
        this.gson = new Gson();
        initFileDb();
    }

    @Override
    public List<Subscription> getSubscriptions() {
        File subscriptionsFile = new File(dbPath);
        if (!subscriptionsFile.exists() || subscriptionsFile.length() == 0) {
            LOGGER.warning("Subscriptions file does not exist or is empty.");
            return new ArrayList<>();
        }
        try (FileReader reader = new FileReader(dbPath)) {
            return gson.fromJson(reader, new com.google.gson.reflect.TypeToken<List<Subscription>>(){}.getType());
        } catch (IOException e) {
            LOGGER.severe("Error loading subscriptions: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void saveSubscriptions(List<Subscription> subscriptions) {
        try (FileOutputStream fileOut = new FileOutputStream(dbPath)) {
            String json = gson.toJson(subscriptions);
            fileOut.write(json.getBytes());
        } catch (IOException e) {
            LOGGER.severe("Error saving subscriptions: " + e.getMessage());
        }
    }

    private void initFileDb() {
        File subscriptionsFile = new File(dbPath);
        if (!subscriptionsFile.exists()) {
            try {
                boolean fileCreated = subscriptionsFile.createNewFile();
                if (fileCreated) {
                    LOGGER.info("Subscriptions file created successfully.");
                } else {
                    LOGGER.warning("Subscriptions file already exists.");
                }
            } catch (IOException e) {
                LOGGER.severe("Error creating subscriptions file: " + e.getMessage());
            }
        }
    }
}

