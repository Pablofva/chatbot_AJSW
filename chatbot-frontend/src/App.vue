<template>
  <div id="app" :style="backgroundImageStyle">
    <InitialChoice v-if="!choiceMade && !isLoggedIn" @choice="handleChoice"/>
    <Registro
        v-else-if="choice === 'register' && !isLoggedIn"
        @user-registered="handleUserAuthenticated"/>
    <LoginUser
        v-else-if="choice === 'login' && !isLoggedIn"
        @login-successful="handleUserAuthenticated"/>
    <HatSelection
        v-else-if="isLoggedIn && !selectedHouse"
        @house-selected="handleHouseSelected"
    />
    <div v-else-if="isLoggedIn && selectedHouse">
      <Banner :house="selectedHouse"/>
      <Chatbot
          v-if="showChatbot"
          :chatbotType="chatbotType"
          :selectedHouse="selectedHouse"
      />
    </div>
  </div>
</template>

<script>
import { mapState, mapGetters, mapMutations, mapActions } from 'vuex';
import InitialChoice from './components/InitialChoice.vue';
import Registro from './components/Registro.vue';
import LoginUser from './components/LoginUser.vue';
import HatSelection from './components/HatSelection.vue';
import Chatbot from './components/Chatbot.vue';
import Banner from './components/Banner.vue';

export default {
  name: 'App',
  components: {
    InitialChoice,
    Registro,
    LoginUser,
    HatSelection,
    Chatbot,
    Banner
  },
  data() {
    return {
      showChatbot: false,
      chatbotType: 'hat',
      choiceMade: false,
      choice: null
    };
  },
  computed: {
    ...mapState(['selectedHouse']),
    ...mapGetters(['isLoggedIn', 'isRegistered', 'userName']),
    backgroundImageStyle() {
      let bgImage = this.selectedHouse
          ? require(`./assets/backgrounds/${this.selectedHouse}.jpg`)
          : require("./assets/backgrounds/default.jpeg");

      return {
        backgroundImage: `url("${bgImage}")`,
        backgroundRepeat: 'repeat',
        backgroundSize: '87px 50px'
      };
    }
  },
  methods: {
    ...mapMutations(['SET_REGISTERED']),
    ...mapActions(['fetchQuestions']),
    handleChoice(choice) {
      this.choiceMade = true;
      this.choice = choice;
    },
    handleUserAuthenticated() {
      console.log('Usuario autenticado (registrado o logueado)');
      this.SET_REGISTERED(true);
      this.fetchQuestions(); // Asegúrate de cargar las preguntas para HatSelection
    },
    handleHouseSelected(house) {
      this.$store.commit('SET_SELECTED_HOUSE', house);
      this.showChatbot = true;
      this.chatbotType = 'prefect';
    },
  }
};
</script>
<style scoped>
#app {
  cursor: url('./assets/cursor.png'), auto;
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  background-color: #333333;
  border-radius: 20px;
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
</style>
