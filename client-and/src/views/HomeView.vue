<script setup>
import { useRouter } from 'vue-router';
import {  post } from "@/assets/utils/api/api-methods.js";

const router = useRouter();

localStorage.clear("userUUID");

function goToMain() {

  const saveUserUUID = uuid => localStorage.setItem("userUUID", uuid);
  const getUserUUID = () => localStorage.getItem("userUUID");
  const isValidUUID = uuid =>
    /^[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}$/i.test(uuid) && uuid;
  const fetchUUID = async () => {
      const response = await post('CreateUser', { userId: '' });
      return isValidUUID(response) && saveUserUUID(response) ||
          console.error('Invalid UUID:', response);
  };

  const assignUUID = async () => {
      try {
          return getUserUUID() || await fetchUUID();
      } catch (error) {
          throw new Error('Error during UUID assignment:', error);
      }
  };

assignUUID().then(uuid =>
    uuid && console.log('Assigned UUID:', uuid)).catch(error =>
    console.error('Error in assignUUID:', error));

  router.push('/main');
}
</script>

<template>
  <div class="welcome-container">
    <h1 class="animated-title">{{'Welcome to LightingFast'}}</h1>
    <p class="subtitle">{{'Sunlight at the speed of light'}}</p>
    <button style="--clr: #145d87;" class="button" @click="goToMain">
      <span class="button-decor"></span>
      <span class="button-content">
        <span class="button__icon">
          <svg
              width="24"
              xmlns="http://www.w3.org/2000/svg"
              fill="none"
              viewBox="0 0 36 36"
          >
            <circle
                fill="url(#icon-lightning-cat_svg__paint0_linear_379_8580)"
                r="18"
                cy="18"
                cx="18"
                opacity="0.5"
            ></circle>
            <path
                fill="#fff"
                d="M22.911 8.791c.46-.87-.621-1.734-1.368-1.093L9.293 18.215c-.627.54-.246 1.568.581 1.568h6.93l-3.838 7.248c-.46.87.622 1.734 1.368 1.093l12.25-10.517c.627-.539.246-1.567-.58-1.567h-6.93L22.91 8.79z"
                clip-rule="evenodd"
                fill-rule="evenodd"
            ></path>
            <defs>
              <linearGradient
                  gradientUnits="userSpaceOnUse"
                  y2="36"
                  x2="18"
                  y1="0"
                  x1="18"
                  id="icon-lightning-cat_svg__paint0_linear_379_8580"
              >
                <stop stop-opacity="0.71" stop-color="#fff" offset="1"></stop>
                <stop stop-opacity="0" stop-color="#fff" offset="1"></stop>
              </linearGradient>
            </defs>
          </svg>
        </span>
        <span class="button__text">{{'Dive In'}}</span>
      </span>
    </button>
  </div>
</template>

<style scoped>
.welcome-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  text-align: center;
  background: linear-gradient(130deg, #000924, #041b38, #093659, #145d87, #228399, #31b0b0);
  background-size: 400% 400%;
  animation: gradientAnimation 12s ease infinite;
  overflow: hidden;
}

@keyframes gradientAnimation {
  0% { background-position: 0 50%; }
  50% { background-position: 100% 50%; }
  100% { background-position: 0 50%; }
}

.animated-title {
  font-size: 3.5rem;
  color: #fff;
  margin-bottom: 1.5rem;
  opacity: 0;
  transform: translateY(-3rem);
  animation: fadeInSlide 2s ease forwards;
  animation-delay: 0.5s;
}

@keyframes fadeInSlide {
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.subtitle {
  font-size: 1.75rem;
  color: #b0c4de;
  margin-bottom: 1.25rem;
  opacity: 0;
  transform: translateY(-1.5rem);
  animation: fadeInSlide 2s ease forwards;
  animation-delay: 1s;
}

.button {
  text-decoration: none;
  line-height: 1;
  border-radius: 1.5rem;
  overflow: hidden;
  position: relative;
  box-shadow: 10px 10px 20px rgba(0, 0, 0, 0.05);
  background-color: #fff;
  color: #121212;
  border: none;
  cursor: pointer;
  font-size: 1.25rem;
  transform: translateY(-1.5rem);
  animation: fadeInSlide 2s ease forwards;
  animation-delay: 1.5s; /* Adjust the delay as needed */
}
.button-decor {
  position: absolute;
  inset: 0;
  background-color: var(--clr);
  transform: translateX(-100%);
  transition: transform 0.3s;
  z-index: 0;
}

.button-content {
  display: flex;
  align-items: center;
  font-weight: 600;
  position: relative;
  overflow: hidden;
}

.button__icon {
  width: 2.8rem;
  height: 3rem;
  background-color: var(--clr);
  display: grid;
  place-items: center;
}

.button__text {
  display: inline-block;
  transition: color 0.2s;
  padding: 0.125rem 1.5rem 0.125rem 0.75rem;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.button:hover .button__text {
  color: #fff;
}

.button:hover .button-decor {
  transform: translate(0);
}
</style>