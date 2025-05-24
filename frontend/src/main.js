import { createApp } from 'vue';
import App from './App.vue';
import router from './router'; // Mengimpor router

createApp(App)
  .use(router) // Menambahkan router ke aplikasi Vue
  .mount('#app');
