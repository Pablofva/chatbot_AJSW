import axios from 'axios';

const API = axios.create({
    baseURL: 'http://localhost:8080/api',
});
// Interceptor para agregar el token JWT a cada solicitud si está presente
API.interceptors.request.use(
    (config) => {
        if (!config.url.endsWith('/users/login') && !config.url.endsWith('/users/register')) {
            const token = localStorage.getItem('token');
            if (token) {
                config.headers['Authorization'] = `Bearer ${token}`;
            }
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);
export const login = (username, password) => {
    return API.post('/users/login', { username, password })
        .then(response => {
            const token = response.data.token;
            localStorage.setItem('token', token); // Almacenar el token en localStorage
            console.log(response);           // para ver la estructura de la respuesta
            console.log(response.data);      // para ver la estructura de 'data'
            return response;
        });
};
export const register = (userData) => {
    return API.post('/users/register', userData)
        .then(response => {
            return response.data;
        });
};

export const getQuestions = () =>
    API.get('/chatbot/questions');

export const submitAnswers = (answers) =>
    API.post('/chatbot/hat-selection', { answers });

export const sendChatMessage = (message, type, house) =>
    API.post('/chatbot/message', { message, type, house });

// Interceptor para manejar errores de respuesta
API.interceptors.response.use(
    response => response,
    error => {
        console.error('API Error:', error);
        // Aquí podrías manejar errores específicos, como redirigir a la página de inicio de sesión en caso de un 401
        if (error.response && error.response.status === 401) {
            // Redirigir al login o mostrar un mensaje
            alert('Sesión expirada. Por favor, inicia sesión de nuevo.');
            localStorage.removeItem('token');
            // Redirigir al login si es necesario
        }
        return Promise.reject(error);
    }
);