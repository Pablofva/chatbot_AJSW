import { createStore } from 'vuex';
import * as api from '@/services/api';

export default createStore({
    state: {
        user: null,
        token: localStorage.getItem('token') || null,
        selectedHouse: null,
        questions: null,
        isLoading: false,
        isRegistered: false,
        error: null,
    },
    mutations: {
        SET_USER(state, user) {
            console.log('Mutation SET_USER called with:', user);
            state.user = user;
        },
        SET_TOKEN(state, token) {
            state.token = token;
            localStorage.setItem('token', token);
        },
        SET_LOADING(state, isLoading) {
            state.isLoading = isLoading;
        },
        SET_ERROR(state, error) {
            state.error = error;
        },
        CLEAR_USER(state) {
            state.user = null;
            state.token = null;
            state.selectedHouse = null;
            state.isRegistered = false;
            localStorage.removeItem('token');
        },
        SET_REGISTERED(state, isRegistered) {
            console.log('Mutation SET_REGISTERED called with:', isRegistered);
            state.isRegistered = isRegistered;
        },
        SET_QUESTIONS(state, questions) {
            state.questions = questions;
        },
        SET_SELECTED_HOUSE(state, house) {
            state.selectedHouse = house;
        },
    },
    actions: {
        async login({ commit }, { username, password }) {
            console.log('Intento de inicio de sesión con:', username);
            commit('SET_LOADING', true);
            try {
                const response = await api.login(username, password);
                console.log('Respuesta del servidor:', response);
                const { user, token } = response.data;
                console.log('Usuario recibido:', user);
                commit('SET_USER', user);
                commit('SET_TOKEN', token);
            } catch (error) {
                console.error('Error en inicio de sesión:', error);
                console.error('Detalles del error:', error.response ? error.response.data : error.message);
                commit('SET_ERROR', error.message);
                throw error;
            } finally {
                commit('SET_LOADING', false);
            }
        },

        async fetchQuestions({ commit }) {
            try {
                const response = await api.getQuestions();
                commit('SET_QUESTIONS', response.data);
            } catch (error) {
                console.error('Error fetching questions:', error);
            }
        },
        async register({ commit }, userData) {
            console.log('Intento de registro con datos:', userData);
            commit('SET_LOADING', true);
            try {
                const user = await api.register(userData);
                console.log('Registro exitoso. Usuario registrado:', user);
                commit('SET_USER', user);
                commit('SET_REGISTERED', true);
                return user;
            } catch (error) {
                console.error('Error en registro:', error);
                commit('SET_ERROR', error.message);
                throw error;
            } finally {
                commit('SET_LOADING', false);
            }
        },
        async submitAnswers({ commit }, answers) {
            commit('SET_LOADING', true);
            try {
                const response = await api.submitAnswers(answers);
              commit('SET_SELECTED_HOUSE',response.data.house);
            } catch (error) {
                console.error('Error al enviar las respuestas de selección de sombrero:', error);
                commit('SET_ERROR', error.message);
                throw error;
            }
        },
        logout({ commit }) {
            commit('CLEAR_USER');
        },
    },
    getters: {
        isLoggedIn: state => {
            console.log('isLoggedIn getter called, user:', state.user);
            return !!state.user && !!state.token;
        },
        userName: state => {
            console.log('userName getter called, user:', state.user);
            return state.user ? state.user.username : null;
        },
        isRegistered: state => {
            console.log('isRegistered getter called, value:', state.isRegistered);
            return state.isRegistered;
        },
    },
});