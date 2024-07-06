<template>
  <div class="login-container">
    <form @submit.prevent="handleLogin" class="login-form">
      <h1>Iniciar sesión</h1>
      <div class="input-container">
        <input v-model="username" placeholder="Nombre de usuario" required>
      </div>
      <div class="input-container">
        <input type="password" v-model="password" placeholder="Contraseña" required>
      </div>
      <button type="submit" class="login-button" :disabled="!isFormValid">
        {{ isLoading ? 'Cargando...' : 'Iniciar sesión' }}
      </button>
      <div v-if="error" class="error-message">{{ error }}</div>
    </form>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex';

export default {
  name: 'LoginUser',
  data() {
    return {
      username: '',
      password: '',
    };
  },
  computed: {
    ...mapState(['isLoading', 'error']),
    isFormValid() {
      const isValid = this.username.length > 0 && this.password.length >= 6;
      console.log('Form is valid:', isValid);
      return isValid;
    },
  },
  methods: {
    ...mapActions(['login']),
    async handleLogin() {
      console.log('handleLogin called');
      if (this.isFormValid) {
        try {
          console.log('Intentando iniciar sesión...');
          const user = await this.login({ username: this.username, password: this.password });
          console.log('Usuario logueado:', user);
          this.$emit('login-successful', user);
        } catch (error) {
          console.error('Login failed:', error);
          // El error ya debería estar en el estado de Vuex, por lo que no necesitamos usar alert aquí
        }
      } else {
        console.log('Formulario no válido');
      }
    },
  },
};
</script>

<style scoped>
@keyframes appear {
  from { opacity: 0; transform: scale(0.8); }
  to { opacity: 1; transform: scale(1); }
}

.login-container {
  animation: appear 1s ease-out;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.login-form {
  background-color: rgba(255, 255, 255, 0.9);
  padding: 30px;
  border-radius: 15px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
  width: 300px;
}

h1 {
  color: #4a4a4a;
  margin-bottom: 20px;
  font-family: 'Harry Potter', sans-serif;
}

.input-container {
  margin-bottom: 15px;
}

input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 16px;
  background-color: rgba(255, 255, 255, 0.8);
}

.login-button {
  background: linear-gradient(45deg, #6a11cb 0%, #2575fc 100%);
  border: none;
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  transition: all 0.3s ease;
  width: 100%;
  margin-top: 10px;
  position: relative;
  min-height: 44px;
}

.login-button:hover {
  background: linear-gradient(45deg, #2575fc 0%, #6a11cb 100%);
  box-shadow: 0 0 15px #2575fc;
  transform: translateY(-3px);
}

.login-button:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.error-message {
  color: #ff0000;
  margin-top: 10px;
  font-size: 14px;
}

/* Estilos para el cursor personalizado */
.login-container,
.login-form,
input,
.login-button {
  cursor: inherit;
}
</style>