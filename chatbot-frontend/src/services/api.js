import axios from 'axios';

const API = axios.create({
    baseURL: 'http://localhost:8080/api',
});

export const login = (username, password) =>
    API.post('/users/login', { username, password });

export const register = (userData) => {
    return API.post('/users/register', userData)
        .then(response => {
            return response.data;
        });
};

export const getQuestions = () =>
    API.get('/chatbot/questions');

export const submitHatSelectionAnswers = (answers) =>
    API.post('/chatbot/hat-selection', { answers });

export const sendChatMessage = (message, type, house) =>
    API.post('/chatbot/message', { message, type, house });

// Interceptor para manejar errores
API.interceptors.response.use(
    response => response,
    error => {
        console.error('API Error:', error);
        return Promise.reject(error);
    }
);