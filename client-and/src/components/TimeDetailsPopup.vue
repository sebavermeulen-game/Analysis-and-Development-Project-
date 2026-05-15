<template>
  <div class="popup-overlay" @click="closePopup">
    <div class="popup-content" @click.stop>
      <h3>Time Details</h3>
      <p>Initial time: 5 hours</p>
      <p>Extra time: 2 hours</p>
      <p>Total time: 7 hours</p>
      <p>Total price: 100 ADCO</p>
      <button @click="closePopup">Close</button>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    closePopup() {
      this.$emit('close');
      this.$router.push('/account');
    }
  }
};
</script>

<style scoped>
.popup-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

.popup-content {
  background: white;
  padding: 3rem;
  border-radius: 0.5rem;
  box-shadow: 0 0.25rem 0.5rem rgba(0, 0, 0, 0.1);
  text-align: center;
}
button {
  background: white;
  padding: 0.5rem;
  border-radius: 0.5rem;
  box-shadow: 0 0.25rem 0.5rem rgba(0, 0, 0, 0.1);
  text-align: center;
  cursor: pointer;
}
button:hover {
  background-color: #070701;
  color: white;
  transform: translateY(-0.0625rem);
  box-shadow: 0 0.25rem 0.75rem rgba(0, 0, 0, 0.2);
  cursor: pointer;
  transition: all 0.3s ease;
}
</style>