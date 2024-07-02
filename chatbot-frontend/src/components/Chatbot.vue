<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="hat-selection-container">
    <div class="user-greeting">
      <h2>¡Hola, {{ userName }}!</h2>
    </div>
    <div class="banner-container">
      <img src="../assets/banners/hatbot.png" alt="Sombrero Seleccionador" class="hat-image">
    </div>
    <form @submit.prevent="submitAnswers">
      <div v-for="(question, index) in questions" :key="index">
        <h3>{{ question.text }}</h3>
        <div v-for="option in question.options" :key="option">
          <label class="option-label">
            <input type="radio" :name="index" :value="option" v-model="answers[index]" required>
            {{ option }}
          </label>
        </div>
      </div>
      <button type="submit" :disabled="!areAllQuestionsAnswered">Enviar</button>
    </form>
  </div>
</template>

<script>
import { mapState, mapGetters, mapActions } from 'vuex';

export default {
  data() {
    return {
      answers: {},
    };
  },
  computed: {
    ...mapState(['questions']),
    ...mapGetters(['userName']),
    areAllQuestionsAnswered() {
      return Object.keys(this.answers).length === this.questions.length;
    },
  },
  methods: {
    ...mapActions(['fetchQuestions', 'submitHatSelectionAnswers']),
    async submitAnswers() {
      if (this.areAllQuestionsAnswered) {
        try {
          const house = await this.submitHatSelectionAnswers(this.answers);
          this.$emit('house-selected', house);
        } catch (error) {
          console.error('Error submitting answers:', error);
        }
      }
    },
  },
  created() {
    this.fetchQuestions();
  },
};
</script>
<style scoped>
.chat-container {
  width: 90%;
  max-width: 900px;
  margin: 20px auto; /* Centra horizontalmente con márgenes automáticos */
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  opacity: 0.94; /* Hace el contenedor medio transparente */
  background-color: #333; /* Fondo gris oscuro */
  color: white; /* Texto blanco para mejor contraste */
}

.chat-title {
  text-align: center;
  margin-bottom: 20px;
  font-size: 2em; /* Ajusta el tamaño de la fuente según sea necesario */
}

.input-container {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

textarea {
  width: 100%;
  height: 100px;
  resize: none;
  padding: 10px;
  margin-bottom: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
}

button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

.message-history {
  max-height: 400px;
  overflow-y: auto;
  border: 1px solid #ccc;
  padding: 10px;
  border-radius: 5px;
  background-color: #444; /* Fondo gris oscuro para los mensajes */
  color: white; /* Texto blanco para mejor contraste */
}

.message {
  margin-bottom: 10px;
  padding: 10px;
  background-color: #555; /* Fondo gris oscuro para los mensajes */
  border-radius: 5px;
  color: white; /* Texto blanco para mejor contraste */
}

@media (max-width: 768px) {
  .chat-container {
    width: 100%;
    margin: 20px auto;
    padding: 10px;
  }

  .chat-title {
    font-size: 1.5em; /* Ajusta el tamaño de la fuente para pantallas más pequeñas */
  }
}
</style>
