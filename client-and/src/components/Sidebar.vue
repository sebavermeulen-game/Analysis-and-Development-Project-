<template>
  <aside class="sidebar">
    <h2>LightingFast</h2>
    <nav>
      <ul>
        <li><a href="/subscriptions">Subscriptions</a></li>
        <li><a href="/about-us">About Us</a></li>
        <li><a href="/account">Account</a></li>
      </ul>
    </nav>
  </aside>
</template>

<script>
export default {};
</script>

<style scoped>
.sidebar {
  width: 15rem;
  background-color: #f4f4f4;
  padding: 1.5rem;
  height: 100%;
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
}

.sidebar h2 {
  font-size: 1.5em;
  color: #333;
}

.sidebar nav ul {
  list-style: none;
  padding: 0;
}

.sidebar nav ul li {
  margin: 1em 0;
}

.sidebar nav ul li a {
  text-decoration: none;
  color: #666;
  font-weight: 500;
  display: block;
  padding: 0.3rem;
  border-radius: 4px;
  transition: background 0.3s;
}

.sidebar nav ul li a:hover {
  background-color: #ddd;
  color: #333;
}
</style>