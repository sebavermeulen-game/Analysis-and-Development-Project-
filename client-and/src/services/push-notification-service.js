import config from "../config.js";

const subscribeOptions = {
    userVisibleOnly: true,
    applicationServerKey: urlB64ToUint8Array(config.vapidPublicKey)
};

async function enablePushNotifications() {
    const permission = await Notification.requestPermission();
    if (permission === "granted") await renewPushnotifications();
}

async function renewPushnotifications() {
    const registration = await navigator.serviceWorker.ready;
    const subscription = await registration.pushManager.subscribe(subscribeOptions);
    postSubscriptionToServer(subscription);
}

function postSubscriptionToServer(subscription) {
    fetch(`${config.serverBaseURL}/api/subscribe`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(subscription)
    });
}


function urlB64ToUint8Array(base64String) {
    const padding = "=".repeat((4 - base64String.length % 4) % 4);
    const base64 = (base64String + padding)
        .replace(/-/g, "+")
        .replace(/_/g, "/");

    const rawData = window.atob(base64);
    const outputArray = new Uint8Array(rawData.length);

    for (let i = 0; i < rawData.length; ++i) {
        outputArray[i] = rawData.charCodeAt(i);
    }
    return outputArray;
}

export { enablePushNotifications, renewPushnotifications };
