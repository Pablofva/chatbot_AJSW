<template>
  <button @click="handleLogout" class="logout-button">Cerrar sesión</button>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  name: 'LogoutButton',
  methods: {
    ...mapActions(['logout']),
    handleLogout() {
      this.logout();
      // Redirigir al usuario a la página de inicio o login
      this.$router.push('/login'); // Asegúrate de tener vue-router configurado
    }
  }
}
</script>

<style scoped>
.logout-button {
  background-color: #f44336;
  color: white;
  padding: 10px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

.logout-button:hover {
  background-color: #d32f2f;
}
</style>