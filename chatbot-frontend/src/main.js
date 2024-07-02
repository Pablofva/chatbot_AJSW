import { createApp } from 'vue';
import App from './App.vue';
import axios from 'axios';
import store from './store'

// Configurar Axios globalmente
axios.defaults.baseURL = 'http://localhost:8081';  // Cambia la URL base si es necesario
axios.defaults.headers.common['Authorization'] = 'AUTH_TOKEN';  // Si necesitas un token de autorización

const app = createApp(App);

// Agregar Axios a las propiedades globales de la aplicación
app.config.globalProperties.$axios = axios;

// Añadir el almacenamiento Vuex al objeto de la instancia Vue
app.use(store);

// Montar la aplicación
app.mount('#app');