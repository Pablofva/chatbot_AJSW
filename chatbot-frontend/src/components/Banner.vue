<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="banner-container">
    <img :src="houseImage" alt="Casa" class="banner-image" />
    <h1>{{ houseName }}</h1>
  </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
  computed: {
    ...mapState({
      house: state => state.selectedHouse
    }),
    houseName() {
      const name = this.house.charAt(0).toUpperCase() + this.house.slice(1).toLowerCase();
      console.log(name);
      return name;
    },
    houseImage() {
      return require(`../assets/Prefect_${this.houseName}.png`);
    }
  }
};
</script>
<style scoped>
.banner-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 20px;
}
.banner-image {
  width: 100%;
  max-width: 600px;
  height: auto;
}
h1 {
  font-size: 2em;
  color: white;
}
</style>
