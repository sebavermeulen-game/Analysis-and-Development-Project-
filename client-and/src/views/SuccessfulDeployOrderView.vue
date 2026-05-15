<template>
  <div class="order-successful-page">
    <h2 class="order-message">Deployment Order Successful!</h2>
    <div class="content">

      <div class="order-details" @click="showPopup">
        <h3>Order Details</h3>
        <p>Deployment order placed successfully</p>
      </div>
      <button class="home-button" @click="goBackToHome">Go Back To Home</button>
    </div>
    <footer class="footer">© 2024 LightingFast. All rights reserved</footer>
    <DeploymentDetailsPopup
        v-if="isPopupVisible"
        @close="isPopupVisible = false"
        :location="orderLocation"
        :duration="orderDuration"
        :range="orderRange"
        :intensity="orderIntensity"
    />
  </div>
</template>

<script>
import DeploymentDetailsPopup from "@/components/DeploymentDetailsPopup.vue";

export default {
  components: { DeploymentDetailsPopup },
  data() {
    return {
      isPopupVisible: false,
      orderLocation: '',
      orderDuration: 0,
      orderRange: 0,
      orderIntensity: 0
    };
  },
  created() {
    this.isPopupVisible = true;
    const storedOrderDetails = localStorage.getItem('orderDetails');
    if (storedOrderDetails) {
      const parsedDetails = JSON.parse(storedOrderDetails);
      this.orderLocation = parsedDetails.location || '';
      this.orderDuration = parsedDetails.duration || 0;
      this.orderRange = parsedDetails.range || 0;
      this.orderIntensity = parsedDetails.intensity || 0;
    }
  },
  methods: {
    goBackToHome() {
      this.$router.push({name: 'main'});
    },
    showPopup() {
      this.isPopupVisible = true;
    },
  },
};
</script>

<style scoped>
.order-successful-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 93vh;
  color: #333;
  font-family: Roboto, sans-serif;
}

.content {
  background-color: #eeeeee;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 3rem 10rem 3rem 10rem;
  border-radius: 0.5rem;
  box-shadow: 0 0.25rem 0.5rem rgba(0, 0, 0, 0.1);
  width: 80%;
  max-width: 31.25rem;
  text-align: center;
}

.order-details {
  width: 50%;
  padding: 1rem;
  background-color: #eeeeee;
  border: 2px solid #555;
  border-radius: 0.3125rem;
  margin-bottom: 1.25rem;
  font-size: 1rem;
  color: #555;
  transition: all 0.3s ease;
}

.order-details:hover {
  background-color: #070701;
  color: white;
  transform: translateY(-0.0625rem);
  box-shadow: 0 0.25rem 0.75rem rgba(0, 0, 0, 0.2);
  cursor: pointer;
}

.home-button {
  background-color: white;
  border: 1px solid #333;
  padding: 0.625rem 1.25rem;
  border-radius: 0.3125rem;
  cursor: pointer;
  font-size: 1rem;
  color: #333;
}

.home-button:hover {
  background-color: #f0f0f0;
}

.footer {
  margin-top: 1.25rem;
  font-size: 0.8rem;
  color: #999;
}
</style>