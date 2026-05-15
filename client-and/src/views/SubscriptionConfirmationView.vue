<template>
    <main>
        <section>
          <h2>You purchased:</h2>
          <div id="planData">
            <p>{{ selectedPlan?.name }}:</p>
            <ul>
              <li><strong>Range:</strong> {{ selectedPlan?.range }}</li>
              <li><strong>Duration:</strong> {{ selectedPlan?.duration }}</li>
              <li><strong>Intensity:</strong> {{ selectedPlan?.intensity }}</li>
              <li><strong>Accept Lighting Request:</strong> {{ selectedPlan?.acceptLightingRequest }}</li>
              <li><strong>Customize Schedule:</strong> {{ selectedPlan?.customizeSchedule }}</li>
              <li><strong>Intensity Control:</strong> {{ selectedPlan?.intensityControl }}</li>
              <li><strong>Radius:</strong> {{ selectedPlan?.radius }}</li>
              <li><strong>Multiple Locations:</strong> {{ selectedPlan?.multipleLocations }}</li>
            </ul>
              
          </div>
          <button @click="confirmSubscription" class="confirmButton">Confirm Subscription</button>
      </section>
    </main>
</template>


<script>
import { post } from "../assets/utils/api/api-methods.js";

export default {
  data() {
    return {
      selectedPlan: null,
    };
  },
  methods: {
    async confirmSubscription() {
      const userId = localStorage.getItem('userUUID');
      const subscriptionId = this.selectedPlan.subscriptionId;

      if (!userId || !subscriptionId) {
        alert('User ID or Subscription ID is missing.');
        return;
      }

      await post('buySubscription', { userId, subscriptionId });

      alert('Subscription confirmed!');
      this.$router.push('/deploy');
     
    },
  },
  mounted() {
    const storedPlan = localStorage.getItem('selectedPlan');
    if (storedPlan) {
      this.selectedPlan = JSON.parse(storedPlan);
    
    } else {
      console.log('No selected plan found in localStorage.');
    }
  },
};


</script>

<style scoped>

main {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 80vh;
}

section {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
section div p{
  font-size: 2rem;
  font-weight: bold;
}

#planData {
  background-color: white;
  width: 25vw;
  border-radius: 1rem;
  margin-bottom: 3rem;
  padding: 1rem 1rem 1rem 1rem;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

li {
  list-style-type: none;
  padding: 0.5rem;
}

ul {
  display: block;
  text-align: left;
  width: max-content;
  margin: auto;
}

.confirmButton {
  --color: #ffffff;
  font-family: inherit;
  display: inline-block;
  background: transparent;
  width: 8em;
  height: 2.6em;
  line-height: 2.5em;
  margin: 10px;
  position: relative;
  cursor: pointer;
  overflow: hidden;
  border: 2px solid var(--color);
  transition: color 0.5s;
  z-index: 1;
  font-size: 1rem;
  border-radius: 6px;
  font-weight: 500;
  color: var(--color);
  text-align: center;
  text-decoration: none;
}

.confirmButton::before {
  content: "";
  position: absolute;
  z-index: -1;
  background: var(--color);
  height: 150px;
  width: 200px;
  border-radius: 50%;
  top: 100%;
  left: 100%;
  transition: all 0.7s;
}

.confirmButton:hover {
  color: #8ca1c6;
}

.confirmButton:hover::before {
  top: -30px;
  left: -30px;
}

.confirmButton:active::before {
  background: #3a0ca3;
  transition: background 0s;
}
</style>