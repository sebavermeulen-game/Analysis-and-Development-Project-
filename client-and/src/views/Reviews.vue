<script>
export default {
  name: "ReviewsPage",
  data() {
    return {
      reviews: [
        { id: 1, author: "Jane Doe", text: "This subscription plan exceeded my expectations" },
        { id: 2, author: "John Smith", text: "Great value for the price" },
        { id: 3, author: "Emily Wilson", text: "Highly recommend this service" },
      ],
      additionalReviews: [
        { id: 4, author: "Sophia V.", text: "Exceeded my expectations" },
        { id: 5, author: "Will S.", text: "Best Platform ever. Lifesaving" },
        { id: 6, author: "Jack S.", text: "Fast Delivery! Amazed as a first-time user" }
      ],
      showMore: false

    };
  },
  methods: {
    viewMoreReviews() {
      if (!this.showMore) {
        this.reviews.push(...this.additionalReviews);
        this.showMore = true;
      }
    }
  }
};
</script>

<template>
  <div class="reviews-page">
    <nav class="navbar">
      <div class="navbar-title">LightingFast</div>
      <ul class="navbar-links">
        <li><a href="/">Home</a></li>
        <li><a href="/subscriptions">Subscriptions</a></li>
      </ul>
    </nav>
    <header class="header">
      <h1>Welcome to LightingFast</h1>
      <p>Order light from satellites at your specified locations</p>
    </header>
    <div class="center-wrapper">
      <section class="reviews-section">
        <h2>Customer Reviews</h2>
        <p>See what our users are saying about our subscription plans</p>
        <button @click="viewMoreReviews">View More Reviews</button>
        <div class="reviews-list">
          <div class="review-card" v-for="review in reviews" :key="review.id">
            <p class="review-author">{{ review.author }}</p>
            <p class="review-rating">⭐⭐⭐⭐⭐</p>
            <p class="review-text">{{ review.text }}</p>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<style scoped>
body {
  overflow-x: hidden;
  margin: 0;
  padding: 0;
}

.reviews-page {
  font-family: Arial, sans-serif;
  color: #333;
  width: 100vw;
  padding: 0;
  margin: 0;
  box-sizing: border-box;
  background: linear-gradient(to bottom, #000924, #041b38, #093659, #145d87, #228399, #31b0b0);
  background-size: 200% 200%;
  animation: gradientAnimation 15s ease infinite;
}

@keyframes gradientAnimation {
  0% { background-position: 0% 50%; }
  50% { background-position: 100% 50%; }
  100% { background-position: 0% 50%; }
}

.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 2rem;
  background-color: #041b38;
  color: #fff;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
}

.navbar-title {
  font-size: 1.5rem;
  font-weight: bold;
  color: #eceef1;
}

.navbar-links {
  list-style-type: none;
  display: flex;
  gap: 1.5rem;
}

.navbar-links a {
  color: #eceef1;
  text-decoration: none;
  transition: color 0.3s ease;
}

.navbar-links a:hover {
  color: #73f0c6;
}

.header {
  text-align: center;
  margin-bottom: 3rem;
}

.header h1 {
  font-size: 3rem;
  color: #eceef1;
  font-weight: bold;
  animation: fadeInSlide 2s ease forwards;
}

.header p {
  font-size: 1.2rem;
  color: #eceef1;
  opacity: 0;
  transform: translateY(-1rem);
  animation: fadeInSlide 2s ease forwards;
  animation-delay: 1s;
}

.center-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
}

.reviews-section {
  width: 100%;
  max-width: 800px;
  text-align: center;
  background-color: rgba(4, 27, 56, 0.9);
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
}

.reviews-section h2 {
  font-size: 2.5rem;
  color: #eceef1;
  margin-bottom: 1rem;
  animation: fadeInSlide 2s ease forwards;
  animation-delay: 0.5s;
}

.reviews-section p {
  color: #b0c4de;
  font-size: 1.2rem;
  margin-bottom: 2rem;
  opacity: 0;
  transform: translateY(-1rem);
  animation: fadeInSlide 2s ease forwards;
  animation-delay: 1s;
}

.reviews-section button {
  background-color: #145d87;
  color: #fff;
  padding: 0.7rem 1.5rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1rem;
  transition: transform 0.4s ease, background-color 0.5s ease;
  margin-bottom: 2rem;
  opacity: 0;
  transform: scale(0.8);
  animation: fadeInScale 2s ease forwards;
  animation-delay: 1.5s;
}

.reviews-section button:hover {
  background-color: #093659;
  transform: scale(1.05);
}

@keyframes fadeInSlide {
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes fadeInScale {
  to {
    opacity: 1;
    transform: scale(1);
  }
}

.reviews-list {
  display: flex;
  flex-wrap: wrap;
  gap: 1.5rem;
  justify-content: center;
}

.review-card {
  width: 220px;
  background-color: #f4f4f4;
  padding: 1.5rem;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  text-align: left;
}

.review-author {
  font-weight: bold;
  color: #041b38;
  margin-bottom: 0.5rem;
}

.review-rating {
  color: #f5c518;
  font-size: 1.2rem;
  margin-bottom: 0.5rem;
}

.review-text {
  color: #555;
  font-size: 0.9rem;
  line-height: 1.4;
}

</style>