<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="register-container">
    <div class="form-header">
      <img src="../assets/haticon.png" alt="Sombrero Seleccionador" class="hat-image" />
      <h2>Registro</h2>
    </div>
    <form @submit.prevent="registerUser" class="register-form">
      <div class="form-group">
        <input v-model="user.name" placeholder="Nombre" required />
      </div>
      <div class="form-group">
        <input v-model="user.email" placeholder="Correo electrónico" required />
      </div>
      <div class="form-group">
        <input v-model="user.username" placeholder="Usuario" required />
      </div>
      <div class="form-group">
        <input type="password" v-model="user.password" placeholder="Contraseña" required />
      </div>
      <div class="form-group">
        <select v-model="user.gender" required>
          <option value="" disabled>Selecciona tu género</option>
          <option value="male">Mago</option>
          <option value="female">Maga</option>
          <option value="elfo_domestico">Elfo</option>
          <option value="animago">Animago</option>
          <option value="fantasma">Fantasma</option>
          <option value="other">Muggle</option>
        </select>
      </div>
      <button type="submit" class="register-button">Registrarse</button>
    </form>
  </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  data() {
    return {
      user: {
        name: '',
        email: '',
        username: '',
        password: '',
        gender: ''
      }
    };
  },
  methods: {
    ...mapActions(['register']),
    async registerUser() {
      try {
        const user = await this.register(this.user);
        console.log('Usuario registrado:', user);
        console.log('Estado después del registro:', this.$store.state.user, this.$store.state.isRegistered);
        if (user) {
          this.$emit('user-registered');
        } else {
          alert('El usuario no pudo ser registrado. Por favor, inténtalo de nuevo.');
        }
      } catch (error) {
        console.error('Error registrando usuario:', error);
        alert('Hubo un error al registrarte. Por favor, inténtalo de nuevo.');
      }
    }
  }
};
</script>


<style scoped>
body {
  background: #3c1053;
  animation: twinkling 10s infinite;
}

@keyframes twinkling {
  0% { background-color: #3c1053; }
  50% { background-color: #5c2a73; }
  100% { background-color: #3c1053; }
}
.register-container {
  animation: appear 1s ease-out;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 90%;
  max-width: 500px;
  margin: 20px auto;
  padding: 20px;
  border: 2px solid #8b4513;
  background-color: #f8f8d8;
  box-shadow: 0 0 15px rgba(139, 69, 19, 0.5);
  border-radius: 10px;

}
@keyframes appear {
  from { opacity: 0; transform: scale(0.8); }
  to { opacity: 1; transform: scale(1); }
}
.form-header {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 20px;
}
.hat-image {
  width: 100px;
  height: auto;
  margin-bottom: 10px;
  animation: float 3s ease-in-out infinite;
}
@keyframes float {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-10px); }
}
h2 {
  text-align: center;
  margin: 0;
  font-size: 1.5em;
  color: #333;
}
.register-form {
  width: 100%;
}
.form-group {
  margin-bottom: 15px;
}
input, select {
  width: 100%;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
  box-sizing: border-box;
  font-size: 1em;
}
input:focus, select:focus {
  box-shadow: 0 0 8px rgba(255, 106, 0, 0.81);
  border-color: rgba(255, 89, 0, 0.59);
  transition: all 0.3s ease;
}
.register-button {
  background: linear-gradient(45deg, #ff8c00, #ff0080);
  transition: all 0.3s ease;
  width: 100%;
  padding: 10px;
  border-radius: 5px;
  border: none;
  color: white;
  font-size: 1em;
  cursor: pointer;
}
.register-button:hover {
  background: linear-gradient(45deg, #ff0080, #ff8c00);
  box-shadow: 0 0 20px #ff69b4;
  transform: scale(1.1);
}
</style>
