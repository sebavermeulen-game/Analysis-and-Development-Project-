<script setup>
import { useRoute } from 'vue-router';
import HeaderComponent from "@/components/HeaderComponent.vue";
import { renewPushnotifications } from "@/services/push-notification-service.js";

const route = useRoute();

if ('serviceWorker' in navigator) {
  navigator.serviceWorker.register(
    import.meta.env.MODE === 'production' ? 'service-worker.js' : 'dev-sw.js?dev-sw'
  );
}

if ("Notification" in window) {
  if (Notification.permission !== "denied") {
    Notification.requestPermission().then(function (permission) {
      if (permission === "granted") {
        renewPushnotifications();
      }
    });
  }
} else {
  alert("Notification is not supported in your browser");
}
</script>

<template>
  <div>
    <HeaderComponent v-if="route.name !== 'home'" />
    <RouterView />
  </div>
</template>


