<template>
  <div id="app" :style="backgroundImageStyle">
    <div class =banner-container :style="bannerImage"></div>

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
    <Chatbot
        v-if="showChatbot && isLoggedIn && selectedHouse"
        :chatbotType="chatbotType"
        :selectedHouse="selectedHouse"
    />
  </div>
</template>

<script>
import { mapState, mapGetters, mapMutations, mapActions } from 'vuex';
import InitialChoice from './components/InitialChoice.vue';
import Registro from './components/Registro.vue';
import LoginUser from './components/LoginUser.vue';
import HatSelection from './components/HatSelection.vue';
import Chatbot from './components/Chatbot.vue';


export default {
  name: 'App',
  components: {
    InitialChoice,
    Registro,
    LoginUser,
    HatSelection,
    Chatbot,

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
    } ,
    bannerImage() {
      let bnImage = this.selectedHouse
          ? require(`@/assets/banners/${this.selectedHouse.toLowerCase()}.png`)
          : require('@/assets/banners/DEFAULT.png'); // Asegúrate de tener esta imagen
      return {
          backgroundImage:`url("${bnImage}")`,
         // backgroundRepeat: 'repeat',

    };},

  },
  methods: {
    ...mapMutations(['SET_REGISTERED']),
    ...mapActions(['fetchQuestions']),
    handleChoice(choice) {
      this.choiceMade = true;
      this.choice = choice;
    },
    handleUserAuthenticated(user) {
      if (user) {
        console.log('Usuario autenticado:', user);
        this.SET_USER(user);
        this.SET_REGISTERED(true);
        this.fetchQuestions();
      } else {
        console.error('Se llamó a handleUserAuthenticated sin un usuario válido');
      }
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
  align-items: center;
  overflow: hidden; /* Asegúrate de que los elementos no se desborden */
   /* Ajusta según sea necesario */
}
.banner-container {
  width: 100%;
  height: 150px; /* Ajusta la altura según sea necesario */
  display: flex;
  padding: 20px;
  transition: all 0.3s ease;
  background-repeat: repeat-x; /* Repetir solo horizontalmente */
  background-size: contain; /* Asegurarse de que toda la imagen sea visible */
}

h1 {
  color: #fff;
  font-family: 'Harry Potter', sans-serif;
  font-size: 2em;
  margin-top: 10px;
  transition: all 0.3s ease;
}

</style>
