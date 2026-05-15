<script setup>
import { ref } from 'vue';
import RangeDetailsPopup from '@/components/RangeDetailsPopup.vue';

const showPopup = ref(false);

function showRangeDetails() {
  showPopup.value = true;
}

function closePopup() {
  showPopup.value = false;
}
</script>

<template>
  <div class="subscription-page">
    <section class="best-plan">
      <div class="plan-info">
        <img src="../assets/images/profile-pic.jpg" alt="logo" class="profile-picture"/>
        <div class="plan-details">
          <h2>Hello, ADRIA-19384729</h2>
        </div>
      </div>
      <div class="change-plans">
        <router-link to="/subscriptions" class="continue-button">Change Plan</router-link>
      </div>
    </section>

    <section class="adjust-account">
      <h2>How many hours do you want to purchase?</h2>
      <div class="account-categories">
        <div class="account-category">
          <h3>Initial distance:</h3>
          <p>150m</p>
        </div>
        <div class="account-category">
          <h3>Extra distance:</h3>
          <p>1000m</p>
        </div>
        <div class="account-category">
          <h3>Total distance:</h3>
          <p>1150m</p>
        </div>
      </div>
      <div class="confirm-order">
        <h3>Total price:</h3>
        <p>15000 ADCO</p>
        <button @click="showRangeDetails" class="continue-button">Confirm order</button>
      </div>
    </section>

    <RangeDetailsPopup v-if="showPopup" @close="closePopup" />
  </div>
</template>

<style scoped>

.subscription-page {
  font-family: Arial, sans-serif;
  color: #333;
  height: 100vh;
}

.best-plan {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 2rem;
  background-color: #1B2A36;
  color: #fff;
  height: 30vh;
}

.plan-info {
  display: flex;
  align-items: center;
  margin-left: 5rem;
  flex-grow: 1 ;
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
  margin-bottom: 2rem;
  font-size: 1rem;
  color: #000;
}

.account-categories {
  display: flex;
  flex-direction: row;
  gap: 1.5rem;
  align-items: stretch;
}

.account-category {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
  height: 100%;
}

.confirm-order {
  align-self: flex-start;
}

h3 {
  font-size: 1.5rem;
  margin-bottom: 1rem;
  color: #ffffff;
}

.account-category p, .confirm-order p {
  font-size: 1.5rem;
  color: #ffffff;
}

.continue-button {
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
  background: none;
}

.continue-button::before{
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

.continue-button:hover{
  color: #8ca1c6;
}

.continue-button:hover::before{
  top: -30px;
  left: -30px;
}

.continue-button:active::before {
  background: #3a0ca3;
  transition: background 0s;
}
</style>