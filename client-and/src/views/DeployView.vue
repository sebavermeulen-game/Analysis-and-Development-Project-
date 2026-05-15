<template>
  <div class="container">
    <h2>Select Location To Deploy Light</h2>
    <OrderOptionsComponent
        :initialDuration="duration"
        :initialRange="range"
        :initialIntensity="intensity"
        :initialShareLocation="shareLocation"
        @cancel="cancel"
        @update:center="updateCenter"
    />
  </div>
</template>

<script>
import OrderOptionsComponent from "../components/OrderOptionsComponent.vue";

export default {
  name: "DeployView",
  components: {
    OrderOptionsComponent,
  },
  data() {
    return {
      duration: 0,
      range: 1,
      intensity: 0,
      shareLocation: false,
      center: null,
    };
  },
  methods: {
    cancel() {
      this.$router.push("/order-light");
    },
    updateCenter(newCenter) {
      this.center = newCenter;
    },
  },
};
</script>

<style>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 100vh;
  width: 100vw;
  padding: 0;
  margin: 0;
  color: black;
}

.container label,
.container p {
  color: white;
}
</style>