import { createStore } from 'vuex';
import * as api from '@/services/api';

export default createStore({
    state: {
        user: null,
        selectedHouse: null,
        isLoading: false,
        isRegistered: false,
        error: null,
    },
    mutations: {
        SET_USER(state, user) {
            console.log('Mutation SET_USER called with:', user);
            state.user = user;
        },
        SET_LOADING(state, isLoading) {
            state.isLoading = isLoading;
        },
        SET_ERROR(state, error) {
            state.error = error;
        },
        CLEAR_USER(state) {
            state.user = null;
            state.selectedHouse = null;
            state.isRegistered = false;
        },
        SET_REGISTERED(state, isRegistered) {
            console.log('Mutation SET_REGISTERED called with:', isRegistered);
            state.isRegistered = isRegistered;
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
                const user = await api.login(username, password);
                console.log('Inicio de sesión exitoso. Usuario:', user);
                commit('SET_USER', user);
            } catch (error) {
                console.error('Error en inicio de sesión:', error);
                commit('SET_ERROR', error.message);
            } finally {
                commit('SET_LOADING', false);
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
        logout({ commit }) {
            commit('CLEAR_USER');
        },
    },
    getters: {
        isLoggedIn: state => !!state.user,
        userName: state => state.user ? state.user.username : null,
        isRegistered: state => state.isRegistered,
    },
});