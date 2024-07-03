<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="hat-selection-container">
    <div class="user-greeting">
      <h2>¡Hola, {{ userName }}!</h2>
    </div>
    <div class="banner-container">
      <img src="../assets/banners/hatbot.png" alt="Sombrero Seleccionador" class="hat-image">
    </div>
    <form @submit.prevent="submitAnswers" v-if="questions && Object.keys(questions).length">
      <div v-for="(question, index) in questions" :key="index">
        <h3>{{ index }}</h3>
        <div v-for="(option, keyIndex) in question" :key="keyIndex">
          <label class="option-label">
            <input type="radio" :name="`answer-${index}`" :value="option" v-model="answers[index]" required>
            {{ option }}
          </label>
        </div>
      </div>
      <button type="submit" :disabled="!areAllQuestionsAnswered">Enviar</button>
    </form>
    <p v-else>Cargando preguntas...</p>
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
      const isAnswersComplete = this.questions && Object.keys(this.questions).length > 0 &&
          Object.keys(this.answers).length === Object.keys(this.questions).length;
      console.log("Are all questions answered?", isAnswersComplete);
      return isAnswersComplete;
    }
  },
  methods: {
    ...mapActions(['fetchQuestions']),
    async submitAnswers() {
      if (this.areAllQuestionsAnswered) {
        try {
          const house = await this.$store.dispatch('submitAnswers', this.answers);
          this.$emit('house-selected', house);
        } catch (error) {
          console.error('Error submitting answers:', error);
        }
      }
    },
  },
  created() {
    this.fetchQuestions().then(() => {
      console.log(this.$store.state.questions);  // imprime las preguntas en el console
    });
  },
};
</script>

<style scoped>
.option-input {
  display: none;
}

.option-label::before {
  content: "⚡";
  display: inline-block;
  margin-right: 10px;
}

.option-input:checked + .option-label::before {
  content: "🔮";
}
.hat-selection-container {
  display: flex;
  flex-direction: column;
  width: 90%;
  max-width: 600px;
  margin: 20px auto;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background-color: #444;
  color: white;
  max-height: 80vh; /* Limita la altura para permitir desplazamiento */
  overflow-y: auto; /* Permite el desplazamiento vertical */
}
.banner-container {
  width: 100%;
  text-align: center;
  margin-bottom: 20px;
}
.hat-image {
  max-width: 100%;
  height: auto;
}
@media (max-width: 768px) {
  .hat-selection-container {
    width: 95%;
    padding: 15px;
  }
  h3 {
    font-size: 1em;
  }
}
</style>
