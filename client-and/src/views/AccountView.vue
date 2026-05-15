<script setup>
import { useRouter } from 'vue-router';
import { ref, onMounted } from 'vue';

const router = useRouter();
const userUUID = ref('');
const subscriptionName = ref('');
const hoursRemaining = ref('');
const range = ref('');

onMounted(() => {
  userUUID.value = localStorage.getItem('userUUID');

  const selectedPlan = localStorage.getItem('selectedPlan');
  if (selectedPlan) {
    const plan = JSON.parse(selectedPlan);
    subscriptionName.value = plan.name;
    hoursRemaining.value = plan.duration;
    range.value = plan.range;
  } else {
    subscriptionName.value = "No subscription found";
    hoursRemaining.value = "0";
    range.value = "0";
  }
});

function cancelSubscription() {
  if (confirm('Are you sure you want to cancel your subscription?')) {
    localStorage.removeItem('selectedPlan');
    router.push('/main');
  }
}
</script>

<template>
  <div class="subscription-page">
    <section class="best-plan">
      <div class="plan-info">
        <img src="../assets/images/profile-pic.jpg" alt="logo" class="profile-picture" />
        <div class="plan-details">
          <h2>Hello<span v-if="userUUID">, {{ userUUID }}</span></h2>
        </div>
      </div>
      <div class="change-plans">
        <router-link to="/subscriptions" class="continue-button">Change Plan</router-link>
      </div>
    </section>

    <section class="adjust-account">
      <h2>Overview</h2>
      <div class="account-categories">
        <!-- Subscription Name -->
        <h3 id="subscriptionName">Subscription name:</h3>
        <p id="name">{{ subscriptionName }}</p>
        <button @click="cancelSubscription" class="continue-button" id="cancelSubscriptionButton">Cancel subscription</button>

        <!-- Hours Remaining -->
        <h3 id="hoursRemaining">Hours remaining:</h3>
        <p id="hours">{{ hoursRemaining }}</p>
        <router-link to="/top-up" class="continue-button" id="hoursButton">Top-up hours</router-link>

        <!-- Current Range -->
        <h3 id="currentRange">Current range:</h3>
        <p id="range">{{ range }}</p>
        <router-link to="/range-extension" class="continue-button" id="extendRange">Extend range</router-link>
      </div>
    </section>
  </div>
</template>

<style scoped>
.subscription-page {
  font-family: Arial, sans-serif;
  height: 100vh;
}

a {
  text-decoration: none;
}

.best-plan {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 2rem;
  background-color: #1A2A36;
  color: #fff;
  height: 30vh;
}

.plan-info {
  display: flex;
  align-items: center;
  margin-left: 5rem;
  flex-grow: 1;
}

.profile-picture {
  width: 7rem;
  height: 7rem;
  background-color: #888;
  border-radius: 50%;
  margin-right: 2.5rem;
}

.adjust-account {
  color: #fff;
  padding: 5rem 5rem 0.5rem 5rem;
  text-align: center;
  margin: auto;
  max-width: 65rem;
}

.adjust-account h2 {
  font-size: 2.25rem;
  color: #ffffff;
  margin-bottom: 2rem;
}

.adjust-account p {
  font-size: 1rem;
  color: #ffffff;
}

.account-categories {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-template-rows: auto auto auto;
  grid-template-areas:
    "subscriptionName name cancelButton"
    "hoursRemaining hours hoursButton"
    "currentRange range rangeButton";
  gap: 1rem;
  justify-items: center; /* Centers items horizontally */
  align-items: center;
}

#subscriptionName {
  grid-area: subscriptionName;
}

#name {
  grid-area: name;
}

#cancelSubscriptionButton {
  grid-area: cancelButton;
}

#hoursRemaining {
  grid-area: hoursRemaining;
}

#hours {
  grid-area: hours;
}

#hoursButton {
  grid-area: hoursButton;
}

#currentRange {
  grid-area: currentRange;
}

#range {
  grid-area: range;
}

#extendRange {
  grid-area: rangeButton;
}

.continue-button {
  background: none;
  --color: #ffffff;
  font-family: inherit;
  display: inline-block;
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

.continue-button::before {
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

.continue-button:hover {
  color: #8ca1c6;
}

.continue-button:hover::before {
  top: -30px;
  left: -30px;
}

.continue-button:active::before {
  background: #3a0ca3;
  transition: background 0s;
}
</style>