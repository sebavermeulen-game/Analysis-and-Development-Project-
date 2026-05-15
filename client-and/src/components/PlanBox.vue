<template>
  <div
      class="plan-box"
      :class="{ selected: isSelected }"
      @click="selectPlan"
  >
    <h3>{{ plan.name }}</h3>
    <div class="plan-divider"></div>
    <ul class="plan-details">
      <li><strong>Range:</strong> {{ plan.range }}</li>
      <li><strong>Duration:</strong> {{ plan.duration }}</li>
      <li><strong>Intensity:</strong> {{ plan.intensity }}</li>
      <li><strong>Accept Lighting Request:</strong> {{ plan.acceptLightingRequest ? 'Yes' : 'No' }}</li>
      <li><strong>Customize Schedule:</strong> {{ plan.customizeSchedule ? 'Yes' : 'No' }}</li>
      <li><strong>Intensity Control:</strong> {{ plan.intensityControl ? 'Yes' : 'No' }}</li>
      <li><strong>Multiple Locations:</strong> {{ plan.multipleLocations }}</li>
    </ul>
    <div class="price">{{ plan.price }}</div>
  </div>
</template>

<script>
export default {
  props: {
    plan: Object,
    isSelected: Boolean,
  },
  methods: {
    selectPlan() {
      
      this.$emit('plan-clicked', this.plan);
  
    },
  },
};
</script>

<style scoped>
.plan-box {
  opacity: 0;
  width: 18rem;
  background-color: #ffffff;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 1.5rem;
  text-align: center;
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.08);
  cursor: pointer;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  display: flex;
  flex-direction: column;
  animation: fadeIn 1s ease-in 0.5s forwards;
}

.plan-box:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.12);
}

.plan-box.selected {
  border-color: #D0F0D0;
  background-color: #D0F0D0;
}

.plan-box h3 {
  font-size: 1.2em;
  color: #222;
  margin-bottom: 0.5rem;
}

.plan-divider {
  height: 1px;
  width: 80%;
  background-color: #eee;
  margin: 1rem auto;
}

.plan-details {
  text-align: left;
  list-style: none;
  padding: 0;
  font-size: 0.9em;
  color: #555;
}

.plan-details li {
  margin: 0.2rem 0;
}

.plan-details li strong {
  color: #333;
  font-weight: 600;
}

.price {
  font-weight: bold;
  color: #2196f3;
  margin-top: 1rem;
  border-top: 1px solid #ddd;
  padding-top: 0.5rem;
  font-size: 1.2em;
}


@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(0);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}
</style>