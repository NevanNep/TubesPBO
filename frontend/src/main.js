import { createApp } from 'vue';
import App from './App.vue';
import router from './router'; // Mengimpor router
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import 'bootstrap'; // jika ingin pakai komponen JS Bootstrap seperti modal, dropdown, dll


createApp(App)
  .use(router) // Menambahkan router ke aplikasi Vue
  .mount('#app');