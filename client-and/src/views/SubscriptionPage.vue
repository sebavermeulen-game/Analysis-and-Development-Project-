<template>
  <div class="subscription-page">
    <header class="header-container">
      <div class="search-bar-container">
        <input
            type="text"
            class="search-bar"
            v-model="searchQuery"
            placeholder="Search plans..."
        />
      </div>
    </header>

    <div class="subscription-container">
      <main class="subscription-main">
        <SubscriptionType
            v-for="(type, index) in subscriptionTypes"
            :key="index"
            :type="type"
            :filteredPlans="filteredPlans[type]"
            :selectedPlan="selectedPlan"
            @plan-selected="updateSelectedPlan"
        >
          <template v-slot:plans>
            <PlanBox
                v-for="(plan, planIndex) in filteredPlans[type]"
                :key="planIndex"
                :plan="plan"
            />
          </template>
        </SubscriptionType>
        <router-link :to="{ name: 'SubscriptionConfirmation' }" v-if="planIsSelected" class="send-button">Choose Subscription</router-link>
      </main>
    </div>
  </div>
</template>

<script>
import { getSubscriptionData } from '@/assets/utils/subscriptions.js';
import PlanBox from '../components/PlanBox.vue';
import SideBar from '../components/Sidebar.vue';
import SubscriptionType from '../components/SubscriptionType.vue';
import TopNav from '../components/TopNav.vue';

export default {
  data() {
    return {
      subscriptionTypes: [],
      cachedData: {},
      searchQuery: '',
      selectedPlan: null,
      planIsSelected: false,
      showConfirmation: false,
    };
  },
  computed: {
    filteredPlans() {
      const result = {};
      for (const type of this.subscriptionTypes) {
        const plans = this.getPlans(type);
        if (!this.searchQuery) {
          result[type] = plans;
        } else {
          result[type] = plans.filter(plan =>
              plan.name.toLowerCase().includes(this.searchQuery.toLowerCase())
          );
        }
      }
      return result;
    },
  },
  methods: {
    getPlans(type) {
      return this.cachedData[type]?.plans || [];
    },
    goToPayment(subscriptionType, planName) {
      this.$router.push({ name: 'PaymentPage', params: { subscriptionType, planName } });
    },
    updateSelectedPlan(plan) {
      this.planIsSelected = true;
      this.selectedPlan = plan;
      this.sendPlanLocalstorage(plan);
    },
    sendPlanLocalstorage(plan) {

      localStorage.setItem('selectedPlan', JSON.stringify(plan));
    },
  },
  async mounted() {
    try {
      const data = await getSubscriptionData();
      this.cachedData = data;
      this.subscriptionTypes = Object.keys(data);
    } catch (error) {
      console.error("Error fetching subscription data:", error);
      this.cachedData = {};
      this.subscriptionTypes = [];
    }
  },
  components: {
    PlanBox,
    SideBar,
    SubscriptionType,
    TopNav,
  },
};
</script>

<style scoped>




.search-bar-container {
  flex: 1;
  display: flex;
  justify-content: flex-end;
}

.search-bar {
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ddd;
  border-radius: 0.25rem;
  margin-right: 2rem;
  margin-top: 0.5rem;
}

.subscription-container {
  display: flex;
}

.subscription-main {  
  flex: 1;
  padding: 2rem;
}

.send-button {
  --color: #ffffff;
  color: var(--color);
  border: 2px solid var(--color);
  position: relative;
  cursor: pointer;
  overflow: hidden;
  padding: 0.75rem 5rem;
  border-radius: 6px;
  margin-top: 2rem;
  display: block;
  margin-left: auto;
  margin-right: auto;
  width: 9vw;
  text-align: center;
  text-decoration: none;
  font-size: 1rem;
  font-weight: 500;
  z-index: 1;
}

.send-button::before {
  content: "";
  position: absolute;
  z-index: -1;
  background: var(--color);
  width: 100%;
  height: 100%;
  top: 0;
  left: -100%;
  transition: all 0.7s;
}

.send-button:hover {
  color: #8ca1c6;
}

.send-button:hover::before {
  left: 0;
  background: #ffffff;
}

.send-button:active::before {
  background: #3a0ca3;
  transition: background 0s;
}

</style>