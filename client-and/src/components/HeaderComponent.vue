<template>
  <header class="header">
    <div class="logo" @click="navigateToMain">
      <img src="../assets/images/lightingfast.png" alt="logo" class="logo-image" />
      <h6>LightingFast</h6>
    </div>
    <nav class="nav-links">
      <router-link to="/main" class="icon-container">
        <img
            :class="{ 'home-icon': true }"
            src="../assets/images/home_icon_static.png"
            alt="Home"
        />
        Home
      </router-link>
      <router-link to="/subscriptions" class="icon-container">
        <img
            :class="{ 'subscription-icon': true }"
            src="../assets/images/subscriptions_icon_static.png"
            alt="subscriptions"
        />
        Subscriptions
      </router-link>
      <router-link to="/deploy" class="icon-container">
        <img
            :class="{ 'deploy-icon': true }"
            src="../assets/images/deploy_icon_static.png"
            alt="deploy"
        />
        Deploy
      </router-link>
      <router-link to="/contact" class="icon-container">
        <img
            :class="{ 'contact-icon': true }"
            src="../assets/images/contact_icon_static.png"
            alt="Contact"
        />
        Contact
      </router-link>
      <router-link to="/faq" class="icon-container">
        <img
            :class="{ 'faq-icon': true }"
            src="../assets/images/faq-icon-static.png"
            alt="FAQ"
        />
        FAQ
      </router-link>
      <router-link to="/account" class="icon-container">
        <img
            :class="{ 'account-icon': true }"
            src="../assets/images/account_icon_static.png"
            alt="account"
        />
        Account
      </router-link>
    </nav>
  </header>
</template>

<script>
export default {
  name: "HeaderComponent",
  methods: {
    navigateToMain() {
      this.$router.push('/main');
    }
  }
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 2rem;
  border-bottom: 0.0625rem solid #ddd;
  font-family: Arial, sans-serif;
  color: #333;
  width: 100vw;
  height: 4vw;
}

a {
  text-decoration: none;
}

.logo {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.nav-links {
  display: flex;
  margin-left: auto;
}

.nav-links a {
  margin: 1rem;
  color: #000000;
  font-size: 1rem;
}

.logo-image {
  width: 4rem;
  height: 4rem;
  border-radius: 50%;
  margin-right: 1rem;
}

.icon-container {
  position: relative;
  display: inline-block;
}

.home-icon, .subscription-icon, .deploy-icon, .contact-icon, .faq-icon, .account-icon {
  display: block;
  width: 40px;
  height: auto;
  transition: opacity 0.3s ease;
  margin: 0 auto;
}

</style>
