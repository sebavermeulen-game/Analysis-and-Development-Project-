/* global clients */

self.addEventListener("push", e => {
    const data = e.data.text();
    self.registration.showNotification("LightingFast Alert", {
        body: data,
    });
});

self.addEventListener("notificationclick", e => {
    e.notification.close();
    clients.openWindow(e.notification.data.url);
});
