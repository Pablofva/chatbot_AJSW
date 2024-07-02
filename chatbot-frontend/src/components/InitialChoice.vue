<template>
  <div class="initial-choice">
    <h1>Bienvenido a Hogwarts</h1>
    <div class="buttons">
      <button @click="$emit('choice', 'register')">Registrarse</button>
      <button @click="$emit('choice', 'login')">Iniciar sesión</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'InitialChoice'
}
</script>

<style scoped>
.initial-choice {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
}

.buttons {
  display: flex;
  gap: 20px;
  margin-top: 20px;
}

button {
  padding: 10px 20px;
  font-size: 1em;
  cursor: pointer;
}
</style>