<template>
  <section class="subscription-type">
    <h2>{{ type }}</h2>
    <div class="plans">
      <PlanBox
          v-for="(plan, planIndex) in filteredPlans"
          :key="planIndex"
          :plan="plan"
          :isSelected="selectedPlan?.name === plan.name"
          @plan-clicked="selectPlan(plan)"
      />
    </div>
  </section>
</template>

<script>
import PlanBox from './PlanBox.vue';

export default {
  props: {
    type: String,
    filteredPlans: Array,
    selectedPlan: Object
  },
  components: {
    PlanBox,
  },
  methods: {
    selectPlan(plan) {
      this.$emit('plan-selected', plan);
    },
  },
};
</script>

<style scoped>
.subscription-type {
  margin-bottom: 3rem;
}

.subscription-type h2 {
  font-size: 1.5em;
  margin: 0 auto 1rem;
  color: #ffffff;
  padding-bottom: 0.5rem;
  text-align: center;
}

.subscription-type h2::after {
  content: '';
  display: block;
  width: 0;
  height: 2px;
  background-color: #eee;
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  animation: showBorder 1s ease-in forwards;
}

.plans {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 2rem;
  justify-items: center;
}

.send-button {
  background-color: #000;
  color: #fff;
  border: none;
  padding: 0.75rem 5rem;
  border-radius: 0.5rem;
  cursor: pointer;
  margin-top: 2rem;
  display: block;
  margin-left: auto;
  margin-right: auto;
}

@keyframes showBorder {
  from {
    width: 0;
  }
  to {
    width: 100%;
  }
}

</style>