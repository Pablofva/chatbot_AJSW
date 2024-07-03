<template>
  <div class="initial-choice">
    <div class="magical-container">
      <h1 class="color-changing">Bienvenido a Hogwarts</h1>
      <div class="buttons">
        <button class="color-changing" @click="$emit('choice', 'register')">Registrarse</button>
        <button class="color-changing" @click="$emit('choice', 'login')">Iniciar sesión</button>
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
  background-color: rgb(255, 255, 255, 0.7);
  background-image: url("../assets/backgrounds/preview.jpg");
  border-radius: 20px;
  padding: 40px;
  text-align: center;
  box-shadow: 0 0 30px rgba(255, 215, 0, 0.5);
  animation: changeColor 10s infinite;
  display: flex;
  flex-direction: column;
  align-items: center;
}


h1.color-changing, .buttons button.color-changing {
  font-family: 'Harry Potter', sans-serif;
  font-size: 2.5em;
  margin-bottom: 30px;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
  animation: changeColor 10s infinite;
}

@keyframes changeColor {
  0% {
    color: #ffd700; /* Amarillo (Gryffindor) */
    border-color: #ffd700;
  }
  25% {
    color: #0b304e; /* Azul oscuro (Ravenclaw) */
    border-color: #0b304e;
  }
  50% {
    color: #1d5e4b; /* Verde (Slytherin) */
    border-color: #1d5e4b;
  }
  75% {
    color: #721c1c; /* Borgoña (Hufflepuff) */
    border-color: #721c1c;
  }
  100% {
    color: #ffd700; /* Amarillo (Gryffindor) */
    border-color: #ffd700;
  }
}

.buttons {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

button {
  padding: 12px 24px;
  font-size: 1.2em;
  cursor: pointer;
  background-color: rgb();
  animation: changeColor 10s infinite;
  background-color: rgba(255, 215, 0, 0.59);
  border-radius: 25px;
  transition: all 0.3s ease;
  font-family: 'Harry Potter', sans-serif;
}

button:hover {
  background-color: rgb(255, 255, 255, 0.7);
  transform: scale(1.05);
}
</style>
