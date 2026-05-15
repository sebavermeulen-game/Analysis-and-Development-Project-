package be.howest.adria.infrastructure.pushnotifications;


import be.howest.adria.infrastructure.pushnotifications.database.SubscriptionDb;
import be.howest.adria.infrastructure.pushnotifications.database.SubscriptionDbImpl;
import be.howest.adria.infrastructure.pushnotifications.server.PushServer;
import be.howest.adria.infrastructure.pushnotifications.server.VapidKeys;
import be.howest.adria.infrastructure.pushnotifications.server.VapidKeys.KeyPairJson;
import be.howest.adria.infrastructure.shared.utils.Config;
import be.howest.adria.infrastructure.shared.utils.PushNotificationInitializationException;
import nl.martijndwars.webpush.PushService;

public class PushNotificationModule {

    public static void init(Config config) throws PushNotificationInitializationException {
        try {
            String vapidKeysPath = config.readSetting("pushnotifications.vapidkeys.path");

            KeyPairJson vapidKeys = VapidKeys.load(vapidKeysPath);

            PushService pushService = new PushService()
                    .setSubject("mailto:your-email@example.com") // Challenge: this should be a configuration value
                    .setPublicKey(vapidKeys.publicKey)
                    .setPrivateKey(vapidKeys.privateKey);

            SubscriptionDb subscriptionDb = new SubscriptionDbImpl(
                    config.readSetting("pushnotifications.subscriptions.db.path"));

            PushServer.initialize(pushService, subscriptionDb);
        } catch (Exception e) {
            throw new PushNotificationInitializationException(
                    "Failed to initialize PushNotificationModule", e);
        }
    }

    private PushNotificationModule() {}
}
