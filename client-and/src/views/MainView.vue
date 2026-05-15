<template>
  <div class="subscription-page">
    <h1>Live Lighting Nearby</h1>
    <MapComponent v-if="center.length" :center="center" :zoom="zoom" :locations="locations" :intensity=0 :range="0" />
    <section class="choose-plan">
      <h2>Subscription Types</h2>
      <div class="plan-categories">
        <div class="plan-category">
          <h3>Individual User</h3>
          <div class="plan-features">
            <p>Up to high-intensity lighting</p>
            <p>Adjustable brightness settings</p>
            <p>Control over lighting schedules</p>
            <p>Ideal for camping, outdoor events, and small gatherings</p>
          </div>
        </div>
        <div class="plan-category">
          <h3>Businesses</h3>
          <div class="plan-features">
            <p>Lighting solutions for business operations and security.</p>
            <p>Medium to high-intensity lighting</p>
            <p>Automated scheduling options</p>
            <p>Perfect for cafés, shops, and small outdoor operations</p>
          </div>
        </div>
        <div class="plan-category">
          <h3>Governments & Large Organizations</h3>
          <div class="plan-features">
            <p>Extensive range options up to 5 km</p>
            <p>Ultra high intensity</p>
            <p>Priority support for uninterrupted service</p>
            <p>Suitable for public safety, rural development, and city-wide lighting</p>
          </div>
        </div>
      </div>
      <router-link to="/subscriptions" class="continue-button">Continue</router-link>
    </section>
  </div>
</template>

<script>
import { ref, watch, onMounted, onBeforeUnmount } from 'vue';
import MapComponent from "../components/MapComponent.vue";
import { get } from "@/assets/utils/api/api-methods.js";

const loadMapState = (center, zoom) => {
  const mapState = localStorage.getItem("mapState");
  if (mapState) {
    const { center: savedCenter, zoom: savedZoom } = JSON.parse(mapState);
    center.value = savedCenter;
    zoom.value = savedZoom;
  }
};

const saveMapState = (center, zoom) => {
  const mapState = {
    center: center.value,
    zoom: zoom.value
  };
  localStorage.setItem("mapState", JSON.stringify(mapState));
};

const handleGeolocationSuccess = (center) => (position) => {
  center.value = [position.coords.latitude, position.coords.longitude];
};

const locationDenied = () => {
  console.log("The user denied the geolocation request.");
};

const initLocation = (handleSuccess, handleError) => {
  if ("geolocation" in navigator) {
    navigator.geolocation.getCurrentPosition(handleSuccess, handleError);
  } else {
    console.log("Geolocation is not supported by this browser.");
  }
};

const fetchLocations = async (locations) => {
  try {
    locations.value = await get('GetAllLocations');
    console.log('Locations:', locations.value);
  } catch (error) {
    console.error("Error fetching locations:", error);
  }
};
const fourteen = 14;
export default {
  components: { MapComponent },
  setup() {
    const center = ref([]);
    const zoom = ref(fourteen);
    const locations = ref([]);

    watchCenter(center, locations);
    initializeMap(center, zoom);
    cleanUpMap(center, zoom);

    return {
      center,
      zoom,
      locations,
      loadMapState,
      saveMapState,
      initLocation,
      handleGeolocationSuccess,
      locationDenied,
      fetchLocations,
    };
  },
};

function watchCenter(center, locations) {
  watch(center, (newCenter) => {
    if (newCenter.length) {
      fetchLocations(locations);
    }
  });
}

function initializeMap(center, zoom) {
  onMounted(() => {
    loadMapState(center, zoom);
    initLocation(handleGeolocationSuccess(center), locationDenied);
  });
}

function cleanUpMap(center, zoom) {
  onBeforeUnmount(() => {
    saveMapState(center, zoom);
  });
}

</script>
<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.subscription-page {
  font-family: Arial, sans-serif;
  color: #333;
}

h1 {
  text-align: center;
  margin-top: 1rem;
  margin-bottom: 1rem;
  font-size: 2.5rem;
  color: #ffffff;
}

.choose-plan {
  color: #333;
  padding: 2rem;
  text-align: center;
}

.choose-plan h2 {
  font-size: 2.25rem;
  margin-bottom: 1rem;
}

.plan-categories {
  display: flex;
  justify-content: space-around;
  gap: 1.5rem;
  margin: 0 auto 5rem;
  max-width: 75rem;
}

.plan-category {
  width: 30%;
  padding: 1rem;
  border: 1px solid #ccc;
  border-radius: 0.5rem;
  background-color: #f8f8f8;
  text-align: left;
}

.plan-category h3 {
  font-size: 1.25rem;
  margin-bottom: 1rem;
  color: #000;
  text-align: center;
}

.plan-features {
  display: grid;
  grid-template-columns: repeat(1, 1fr);
  gap: 1rem;
}

.plan-features p {
  padding: 1rem;
  background-color: #e6e6e6;
  border-radius: 0.25rem;
  text-align: center;
  font-size: 1rem;
  color: #333;
}

.plan-features p:hover {
  background-color: #d1d1d1;
}

.continue-button {
  --color: #ffffff;
  font-family: inherit;
  display: inline-block;
  width: 8rem;
  height: 2.6rem;
  line-height: 2.5rem;
  margin: 0.625rem;
  position: relative;
  cursor: pointer;
  overflow: hidden;
  border: 2px solid var(--color);
  transition: color 0.5s;
  z-index: 1;
  font-size: 1rem;
  border-radius: 0.375rem;
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
  height: 9.375rem;
  width: 12.5rem;
  border-radius: 50%;
  top: 100%;
  left: 100%;
  transition: all 0.7s;
}

.continue-button:hover {
  color: #8ca1c6;
}

.continue-button:hover::before {
  top: -1.875rem;
  left: -1.875rem;
}

.continue-button:active::before {
  background: #3a0ca3;
  transition: background 0s;
}
</style>
