<template>
  <section class="light-settings">
    <h2>Adjust Light Parameters</h2>
    <p class="subtext">Set the duration, range and intensity for the light</p>
    <form @submit.prevent="submitForm">
      <div class="input-group-container">
        <div class="input-group">
          <label for="duration">Duration</label>
          <input
              id="duration"
              v-model="duration"
              type="number"
              placeholder="Enter duration in minutes"
              required
              min="1"
          />
        </div>
        <div class="input-group">
          <label for="range">Range</label>
          <input
              id="range"
              v-model="range"
              type="number"
              placeholder="Enter range in metres"
              required
              min="1"
          />
        </div>
        <div class="input-group">
          <label for="intensity">Light Intensity</label>
          <input
              id="intensity"
              v-model="intensity"
              type="number"
              placeholder="Enter intensity level in percentage"
              required
              min="0"
              max="100"
              @input="validateIntensity"
          />
        </div>
      </div>
      <div class="share-location">
        <label for="share-location-checkbox">Share location to homepage:</label>
        <label class="switch">
          <input id="share-location-checkbox" type="checkbox" v-model="shareLocation" class="toggle">
          <span class="slider"></span>
          <span class="card-side"></span>
        </label>
      </div>
      <div class="button-group">
        <button type="button" @click="cancel" class="cancel-button">Cancel</button>
        <button type="submit" class="save-button">Save</button>
      </div>
    </form>
  </section>
</template>

<script>
export default {
  name: "ParameterFormComponent",
  props: {
    initialDuration: {
      type: Number,
      required: true
    },
    initialRange: {
      type: Number,
      required: true
    },
    initialIntensity: {
      type: Number,
      required: true
    },
    initialShareLocation: {
      type: Boolean,
      required: true
    }
  },
  data() {
    return {
      duration: this.initialDuration,
      range: this.initialRange,
      intensity: this.initialIntensity,
      shareLocation: this.initialShareLocation,
    };
  },
  watch: {
    duration(newVal) {
      this.$emit("update:duration", newVal);
    },
    range(newVal) {
      this.$emit("update:range", newVal);
    },
    intensity(newVal) {
      this.$emit("update:intensity", newVal);
    },
    shareLocation(newVal) {
      this.$emit("update:shareLocation", newVal);
    },
  },
  methods: {
    submitForm() {
      this.$emit("submitParameters", {
        duration: this.duration,
        range: this.range,
        intensity: this.intensity,
        shareLocation: this.shareLocation,
      });
    },
    cancel() {
      this.$emit("cancel");
    },
    validateIntensity() {
      return this.intensity > 100 && (this.intensity = 100) ||
          this.intensity < 0 && (this.intensity = 0);
    },
  },
};
</script>

<style scoped>
h2 {
  text-align: center;
  font-size: 1.5rem;
  margin-bottom: 0.625rem;
}

.subtext {
  text-align: center;
  font-size: 1rem;
  margin-bottom: 1.25rem;
}

.input-group-container {
  display: flex;
  justify-content: center;
  gap: 2rem;
  margin-top: 1.25rem;
  flex-wrap: wrap;
}

.input-group {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 1.25rem;
}

.input-group label {
  font-size: 0.875rem;
  margin-bottom: 0.25rem;
}

.input-group input {
  width: 18rem;
  padding: 0.625rem;
  font-size: 1rem;
  border: 0.0625rem solid #ccc;
  border-radius: 0.3125rem;
  text-align: center;
}
.share-location {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 1.25rem;
  gap: 0.5rem;
  font-size: 1rem;
}
.button-group {
  display: flex;
  justify-content: center;
  gap: 1.25rem;
  margin-top: 1.25rem;
  margin-bottom: 1.25rem;
}

.cancel-button,
.save-button {
  width: 7.5rem;
  padding: 0.625rem;
  font-size: 1rem;
  border-radius: 0.3125rem;
  cursor: pointer;
  font-family: inherit;
  position: relative;
  overflow: hidden;
  z-index: 1;
  text-align: center;
  transition: color 0.5s;
}

.cancel-button {
  --color: #333;
  color: var(--color);
  background: #ffffff;
  border: 2px solid var(--color);
}

.cancel-button::before {
  content: "";
  position: absolute;
  z-index: -1;
  background: var(--color);
  height: 100%;
  width: 100%;
  top: 0;
  left: -100%;
  transition: all 0.7s ease;
}

.cancel-button:hover {
  color: #ffffff;
}

.cancel-button:hover::before {
  left: 0;
}

.save-button {
  --color: #ffffff;
  background: #000000;
  border: 2px solid var(--color);
  color: var(--color);
}

.save-button::before {
  content: "";
  position: absolute;
  z-index: -1;
  background: var(--color);
  height: 100%;
  width: 100%;
  top: 0;
  left: -100%;
  transition: all 0.7s ease;
}

.save-button:hover {
  color: #8ca1c6;
}

.save-button:hover::before {
  left: 0;
}

.save-button:active::before {
  background: #3a0ca3;
  transition: background 0s;
}

.switch {
  position: relative;
  display: inline-block;
  width: 60px;
  height: 34px;
}

.switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  transition: .4s;
  border-radius: 34px;
}

.slider:before {
  position: absolute;
  content: "";
  height: 26px;
  width: 26px;
  left: 4px;
  bottom: 4px;
  background-color: white;
  transition: .4s;
  border-radius: 50%;
}

input:checked + .slider {
  background-color: #2196F3;
}

input:checked + .slider:before {
  transform: translateX(26px);
}

.card-side {
  display: none;
}

</style>
