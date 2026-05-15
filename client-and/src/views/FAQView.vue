<template>
  <div class="faq-page">
    <header class="faq-header">
      <h6>Frequently Asked Questions</h6>
      <p>Find quick answers to common questions about LightingFast services.</p>
    </header>

    <section class="faq-section">
      <div v-for="(faq, index) in faqs" :key="index" class="faq-item">
        <h3
            class="faq-question"
            :class="{ active: faq.open }"
            @click="toggleFAQ(index)"
        >
          {{ faq.question }}
        </h3>
        <p v-if="faq.open" class="faq-answer">{{ faq.answer }}</p>
      </div>
    </section>
  </div>
</template>

<script>

export default {
  data() {
    return {
      faqs: this.getFAQData(),
    };
  },

  methods: {
    getFAQData() {
      return [
        this.createFAQ("What is LightingFast?", "LightingFast uses satellite technology to provide lighting to remote outdoor locations. Whether it's for events, emergencies, or personal use, we offer fast, eco-friendly lighting solutions."),
        this.createFAQ("How does LightingFast work?", "Our service allows you to order light via our platform. We use advanced technology to reflect lighting from the sun to your desired location based on your plan and preferences."),
        this.createFAQ("How can I place a lighting request?", "You can place a request via our platform by logging into your account, selecting your plan, and specifying the location, time, and intensity of the light you need."),
        this.createFAQ("Can I schedule lighting in advance?",     "Our service allows you to request lighting in real-time, only at the moment you need it. Scheduling in advance is not supported. Simply place your order when you need lighting, and it will be delivered immediately."),
        this.createFAQ("What should I do if the light isn’t working as expected?", "First, check your settings to ensure the request was processed correctly. If the issue persists, contact our support team for troubleshooting."),
      ];
    },

    createFAQ(question, answer) {
      return {
        question: question,
        answer: answer,
        open: false,
      };
    },

    toggleFAQ(index) {
      this.faqs[index].open = !this.faqs[index].open;
    },
  },
};
</script>

<style scoped>
.faq-page {
  font-family: Arial, sans-serif;
  padding: 2rem;
  color: #fff;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
}

header.faq-header {
  text-align: center;
  margin-bottom: 2rem;
  background-color: rgba(255, 255, 255, 0.1);
  padding: 1rem;
  border-radius: 0.625rem;
  backdrop-filter: blur(0.313rem);
}

header.faq-header h6 {
  font-size: 2rem;
  margin-bottom: 0.5rem;
  color: #fff;
}

header.faq-header p {
  font-size: 1.2rem;
  color: #cce3ff;
}


.faq-section {
  width: 100%;
}

.faq-item {
  background: #ffffff20;
  border: 0.063rem solid #ffffff50;
  border-radius: 0.5rem;
  margin-bottom: 1rem;
  overflow: hidden;
  transition: all 0.3s ease;
}


.faq-question {
  cursor: pointer;
  padding: 1rem;
  font-size: 1.2rem;
  font-weight: bold;
  background: #ffffff10;
  color: #fff;
  transition: background 0.3s ease;
}

.faq-question:hover {
  background: #ffffff30;
}

.faq-question.active {
  background: #ffffff50;
}

.faq-answer {
  padding: 1rem;
  font-size: 1rem;
  color: #dfeaff;
  background: #ffffff10;
  border-top: 0.063rem solid #ffffff30;
}
</style>
