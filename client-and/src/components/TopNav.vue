<template>
  <div class="top-nav">
    <h1>Subscription Details</h1>
    <input
        type="search"
        :value="searchQuery"
        @input="$emit('update:searchQuery', $event.target.value)"
        placeholder="Search..."
    />
  </div>
</template>

<script>
export default {
  props: {
    searchQuery: String,
  },
};
</script>

<style scoped>
.top-nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  border-bottom: 1px solid #ddd;
  background-color: #ffffff;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.top-nav h1 {
  font-size: 1.5em;
  color: #555;
}

.top-nav input {
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}
</style>