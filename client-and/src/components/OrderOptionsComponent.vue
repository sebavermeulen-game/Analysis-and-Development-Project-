<template>
  <div class="order-options-page">
    <section v-if="!center" class="dots-container">
      <div class="dot"></div>
      <div class="dot"></div>
      <div class="dot"></div>
      <div class="dot"></div>
      <div class="dot"></div>
    </section>
    <section v-else class="map-selection">
      <MapComponent :center="center" :zoom="zoom" :range="range" :intensity="intensity" @update:center="updateCenter" />
    </section>

    <section class="light-settings">
      <ParameterFormComponent
          :initialDuration="duration"
          :initialRange="range"
          :initialIntensity="intensity"
          :initialShareLocation="shareLocation"
          @submitParameters="handleParametersSubmit"
          @cancel="cancel"
          @update:duration="updateDuration"
          @update:range="updateRange"
          @update:intensity="updateIntensity"
          @update:shareLocation="updateShareLocation"
      />
    </section>
  </div>
</template>

<script>
import MapComponent from "./MapComponent.vue";
import ParameterFormComponent from "./ParameterFormComponent.vue";
import { post } from "../assets/utils/api/api-methods.js";

export default {
  name: "OrderOptionsComponent",
  components: {
    MapComponent,
    ParameterFormComponent,
  },
  data() {
    return {
      duration: 0,
      range: 1,
      intensity: 0,
      shareLocation: false,
      zoom: 14,
      center: null,
      userId: null,
    };
  },
  mounted() {
    this.initLocation();
    this.userId = localStorage.getItem("userUUID");
  },
  methods: {
    handleParametersSubmit({ duration, range, intensity, shareLocation }) {
      this.duration = duration;
      this.range = range;
      this.intensity = intensity;
      this.shareLocation = shareLocation;
      this.save();
    },
    async save() {
      if (!this.center) {
        alert("Please place a marker on the map to select a location.");
        return;
      }
      localStorage.setItem('orderDetails', JSON.stringify({
            location: this.center,
            duration: this.duration,
            range: this.range,
            intensity: this.intensity,
          }));
      const orderData = this.createOrderData();
      try {
        const response = await this.submitOrder(orderData);
        this.handleOrderResponse(response);
      } catch (error) {
        this.handleError(error);
      }
    },
    createOrderData() {
      return {
        range: this.range,
        intensity: this.intensity,
        userId: this.userId,
        duration: this.duration,
        location: {
          lat: this.center[0],
          lng: this.center[1],
        },
      };
    },
    async submitOrder(orderData) {
      if (!this.center) {
        alert("Please place a marker on the map to select a location.");
        return;
      }
      return await post("order-options", orderData);
    },
    handleOrderResponse(response) {
      const { orderId } = response.map;
      const orderIdPattern = /^\d+$/;

      if (orderIdPattern.test(orderId)) {
        localStorage.setItem("orderId", orderId);
        this.$router.push("/successful-deploy-order");
      } else {
        alert(orderId);
      }
    },
    handleError(error) {
      console.error("Failed to submit order:", error);
      alert("Network error. Please try again later.");
    },
    cancel() {
      this.$emit("cancel");
    },
    initLocation() {
      if ("geolocation" in navigator) {
        navigator.geolocation.getCurrentPosition(
            this.handleGeolocationSuccess,
            this.locationDenied
        );
      } else {
        console.log("Geolocation is not supported by this browser.");
      }
    },
    handleGeolocationSuccess(position) {
      this.center = [position.coords.latitude, position.coords.longitude];
      this.$emit("update:center", this.center);
    },
    locationDenied() {
      console.log("The user denied the geolocation request.");
    },
    updateCenter(newCenter) {
      this.center = newCenter;
      this.$emit("update:center", newCenter);
    },
    updateDuration(newDuration) {
      this.duration = newDuration;
    },
    updateRange(newRange) {
      this.range = newRange;
    },
    updateIntensity(newIntensity) {
      this.intensity = newIntensity;
    },
    updateShareLocation(newShareLocation) {
      this.shareLocation = newShareLocation;
    },
  },
};
</script>

<style scoped>
.order-options-page {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  width: 100vw;
}

h1 {
  text-align: center;
  font-weight: bold;
}

.map-selection {
  width: 100vw;
}

.light-settings {
  margin-top: 2.5rem;
  text-align: center;
}
</style>