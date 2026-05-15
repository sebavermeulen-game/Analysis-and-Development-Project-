<template>
  <div class="light-adjustment-container">
    <MapComponent />
    <ParameterFormComponent
        :initialDuration="currentSettings.duration"
        :initialIntensity="currentSettings.intensity"
        @submitParameters="handleSubmit"
    />
  </div>
</template>

<script>
import { get, patch } from "../assets/utils/api/api-methods.js";
import MapComponent from "../components/MapComponent.vue";
import ParameterFormComponent from "../components/ParameterFormComponent.vue";
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

export default {
  name: "LightAdjustmentView",
  components: { MapComponent, ParameterFormComponent },
  setup() {
    const router = useRouter();
    const currentSettings = ref({ duration: "", intensity: "", location:{latitude:"", longitude:""} });

    onMounted(async () => {
      try {
        const response = await get("/currentLightSettings");
        currentSettings.value.duration = response.duration;
        currentSettings.value.intensity = response.intensity;
        currentSettings.value.location = response.location;
      } catch (error) {
        console.error("Failed to fetch current light settings:", error);
      }
    });

    async function handleSubmit({ duration, intensity, location }) {
      try {
        await patch("/adjustLight", { duration, intensity, location, userId });
        router.push("/faceID-verification");
      } catch (error) {
        console.error("Failed to adjust light:", error);
      }
    }

    return { handleSubmit, currentSettings };
  },
};
</script>

<style scoped>
.light-adjustment-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>