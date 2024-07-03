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
      <button type="submit" class="login-button" :disabled="isLoading || !isFormValid">
        {{ isLoading ? 'Cargando...' : 'Iniciar sesión' }}
      </button>
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
      return this.username.length > 0 && this.password.length >= 8;
    },
  },
  methods: {
    ...mapActions(['login']),
    async handleLogin() {
      if (this.isFormValid) {
        try {
          console.log('Intentando iniciar sesión...');
          const user = await this.login({ username: this.username, password: this.password });
          console.log('Usuario logueado:', user);
          this.$emit('login-successful', user);
        } catch (error) {
          console.error('Login failed:', error);
          alert('Error al iniciar sesión. Por favor, verifica tus credenciales e intenta de nuevo.');
          // No emitas el evento login-successful aquí
        }
      }
    },
  },
};
</script>

<style scoped>
.login-container {
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

.loader {
  border: 4px solid #f3f3f3;
  border-top: 4px solid #3498db;
  border-radius: 50%;
  width: 24px;
  height: 24px;
  animation: spin 1s linear infinite;
  margin: 0 auto;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

/* Estilos para el cursor personalizado */
.login-container,
.login-form,
input,
.login-button {
  cursor: inherit;
}
</style>