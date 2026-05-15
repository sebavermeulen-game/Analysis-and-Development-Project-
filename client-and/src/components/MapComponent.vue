<template>
  <div id="map" style="height: 60vh; width: 100vw"></div>
</template>

<script>
import L from 'leaflet';

export default {
  name: "MapComponent",
  props: {
    center: {
      type: Array,
      required: true,
    },
    zoom: {
      type: Number,
      required: true,
    },
    range: {
      type: Number,
      required: true,
    },
    intensity: {
      type: Number,
      required: true,
    },
    locations: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      mapObject: null,
      marker: null,
      radiusCircle: null,
    };
  },
  mounted() {
    this.initMap();
  },
  watch: {
    range(newRange) {
      if (this.radiusCircle) {
        this.radiusCircle.setRadius(newRange);
      }
    },
    intensity(newIntensity) {
      if (this.radiusCircle) {
        this.updateCircleColor(newIntensity);
      }
    },
    locations: {
      handler(newLocations) {
        this.updateLocations(newLocations);
      },
      immediate: true,
    },
  },
  methods: {
    initMap() {
      if (this.center) {
        this.mapObject = L.map("map").setView(this.center, this.zoom);
        L.tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
          attribution: '&copy; OpenStreetMap contributors',
        }).addTo(this.mapObject);

        this.mapObject.on("click", this.handleMapClick);
        this.createMarker(this.center[0], this.center[1]);
      }
    },
    handleMapClick(event) {
      const { lat, lng } = event.latlng;
      this.$emit("update:center", [lat, lng]);
      this.createRadius(lat, lng);
    },
    createMarker(lat, lng) {
      const ICON_SIZE = 24;
      const ICON_ANCHOR_X = 12;
      const ICON_ANCHOR_Y = 24;
      const POPUP_ANCHOR_X = 0;
      const POPUP_ANCHOR_Y = -24;

      const icon = L.divIcon({
        html: '<span class="material-icons">person_pin</span>',
        className: "person-pin",
        iconSize: [ICON_SIZE, ICON_SIZE],
        iconAnchor: [ICON_ANCHOR_X, ICON_ANCHOR_Y],
        popupAnchor: [POPUP_ANCHOR_X, POPUP_ANCHOR_Y],
      });

      if (!this.marker) {
        this.marker = L.marker([lat, lng], { icon, draggable: false })
            .addTo(this.mapObject)
            .bindPopup(this.getPopupContent());

        this.attachPopupEvent();
      }
    },
    getPopupContent() {
      return `
      <div style="font-family: Arial, sans-serif; text-align: center; color: #333;">
        <h4 style="color: #4CAF50;">You are here</h4>
        <p style="font-size: 14px; color: #777; margin-bottom: 20px;">Click below to create a radius:</p>
        <button id="create-radius-btn"
                style="background-color: #4CAF50; color: white; border: none; padding: 10px 20px; border-radius: 5px; cursor: pointer; font-size: 16px;">
            Create Radius
        </button>
      </div>`;
    },
    attachPopupEvent() {
      this.marker.on("popupopen", () => {
        const createRadiusBtn = document.getElementById("create-radius-btn");
        createRadiusBtn.addEventListener("click", () => {
          this.createRadius(this.marker.getLatLng().lat, this.marker.getLatLng().lng);
        });
      });
    },
    createRadius(lat, lng) {
      const radius = this.range || 1;

      if (this.radiusCircle) {
        this.mapObject.removeLayer(this.radiusCircle);
      }

      this.radiusCircle = L.circle([lat, lng], {
        color: "black",
        fillColor: this.getColorFromIntensity(this.intensity),
        fillOpacity: 0.3,
        radius: radius,
      }).addTo(this.mapObject);
    },
    getColorFromIntensity(intensity) {
      const MAX_RGB_VALUE = 255;
      const INTENSITY_TO_RGB_SCALING_FACTOR = 2.55;
      const colorValue = Math.min(MAX_RGB_VALUE, intensity * INTENSITY_TO_RGB_SCALING_FACTOR);
      return `rgb(${colorValue}, ${colorValue}, 0)`;
    },
    updateCircleColor(intensity) {
      if (this.radiusCircle) {
        this.radiusCircle.setStyle({
          fillColor: this.getColorFromIntensity(intensity),
        });
      } else {
        console.warn("Radius circle is not initialized. Cannot update color.");
      }
    },
    updateLocations(locations) {
      locations.forEach(location => {
        L.marker([location.latitude, location.longitude]).addTo(this.mapObject);
        L.circle([location.latitude, location.longitude], {
          radius: location.range,
          color: 'black',
          fillColor: this.getColorFromIntensity(location.intensity),
          fillOpacity: 0.3,
        }).addTo(this.mapObject);
      });
    },
  },
};
</script>

<style scoped>
#map {
  height: 60vh;
  width: 100vw;
}
</style>