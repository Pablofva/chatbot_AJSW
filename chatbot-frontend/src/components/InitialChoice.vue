<template>
  <div class="initial-choice">
    <div class="magical-container">
      <h1>Bienvenido a Hogwarts</h1>
      <div class="buttons">
        <button @click="$emit('choice', 'register')">Registrarse</button>
        <button @click="$emit('choice', 'login')">Iniciar sesión</button>
      </div>
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
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;

}

.magical-container {
  background-color: rgba(0, 0, 0, 0.7);
  border-radius: 70%;
  padding: 40px;
  text-align: center;
  box-shadow: 0 0 30px rgba(255, 215, 0, 0.5);
  animation: glow 2s infinite alternate;
}

@keyframes glow {
  from {
    box-shadow: 0 0 30px rgba(255, 215, 0, 0.5);
  }
  to {
    box-shadow: 0 0 50px rgba(255, 215, 0, 0.8);
  }
}

h1 {
  font-family: 'Harry Potter', sans-serif;
  font-size: 2.5em;
  color: #ffd700;
  margin-bottom: 30px;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
}

.buttons {
  display: inline-block;
  flex-direction: column;
  gap: 20px;
}

button {
  padding: 12px 24px;
  font-size: 1.2em;
  cursor: pointer;
  background-color: transparent;
  color: #ffd700;
  border: 2px solid #ffd700;
  border-radius: 25px;
  transition: all 0.3s ease;
  font-family: 'Harry Potter', sans-serif;
}

button:hover {
  background-color: rgba(255, 215, 0, 0.2);
  transform: scale(1.05);
}
</style>
