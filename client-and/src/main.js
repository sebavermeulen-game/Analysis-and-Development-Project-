import './assets/main.css';
import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import { get} from "@/assets/utils/api/api-methods.js";


const app = createApp(App);
app.use(router).mount('#app');

get('health').then(() =>
    console.log("API health check OK")).catch(err =>
    console.error("API health check failed:", err));
