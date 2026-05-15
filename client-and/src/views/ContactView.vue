<template>
  <div class="contact-page">
    <section class="contact-form">
      <h2>Contact Us</h2>
      <form @submit.prevent="handleSubmit">
        <div class="form-group" :class="{ 'is-invalid': errors.name }">
          <label for="name">Name</label>
          <input
              type="text"
              id="name"
              v-model="form.name"
              placeholder="Enter your full name"
          />
          <small v-if="errors.name">{{ errors.name }}</small>
        </div>
        <div class="form-group" :class="{ 'is-invalid': errors.email }">
          <label for="email">Email</label>
          <input
              type="email"
              id="email"
              v-model="form.email"
              placeholder="Enter your email address"
          />
          <small v-if="errors.email">{{ errors.email }}</small>
        </div>
        <div class="form-group" :class="{ 'is-invalid': errors.message }">
          <label for="message">Message</label>
          <textarea
              id="message"
              v-model="form.message"
              placeholder="Write your message here"
          ></textarea>
          <small v-if="errors.message">{{ errors.message }}</small>
        </div>
        <button type="submit" class="submit-btn">Submit</button>
      </form>
      <div class="contact-info">
        <h2>Contact Information</h2>
        <p>Address: Rijselstraat 5, Adria</p>
        <p>Phone: +098-765-4321</p>
        <p>Email: support@lightingfast.be</p>
        <p>Support Hours: Subscription Based</p>
      </div>
    </section>
  </div>
</template>

<script>

export default {
  name: "ContactView",
  data() {
    return {
      form: {
        name: "",
        email: "",
        message: "",
      },
      errors: {},
    };
  },
  methods: {
    validateForm() {
      this.errors = {};

      if (!this.form.name.trim()) {
        this.errors.name = "Name is required.";
      }

      if (!this.form.email.trim()) {
        this.errors.email = "Email is required.";
      } else if (!this.isValidEmail(this.form.email)) {
        this.errors.email = "Invalid email format.";
      } else {
        delete this.errors.email;
      }

      if (!this.form.message.trim()) {
        this.errors.message = "Message is required.";
      }

      return Object.keys(this.errors).length === 0;
    },
    isValidEmail(email) {
      const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
      return emailRegex.test(email);
    },
    handleSubmit() {
      if (this.validateForm()) {
        alert("Your message has been sent!");

        Object.keys(this.form).forEach((key) => {
          this.form[key] = "";
        });

        this.$router.push({ name: "main" });
      }
    },
  },
};
</script>

<style scoped>
.contact-page {
  font-family: Arial, sans-serif;
  height: 100vh;
}

.contact-form {
  padding: 2rem;
  background-color: #f9f9f9;
  max-width: 37.5rem;
  margin: 2rem auto;
  border-radius: 8px;
  box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.1);
}

.contact-form h2 {
  text-align: center;
  margin-bottom: 1.5rem;
  color: #333;
}

.form-group {
  margin-bottom: 1rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  color: #333;
}

.form-group input,
.form-group textarea {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ccc;
  border-radius: 0.25rem;
  font-size: 1rem;
}

.form-group textarea {
  resize: none;
  height: 6.25rem;
}

.form-group.is-invalid input,
.form-group.is-invalid textarea {
  border-color: red;
}

.form-group small {
  color: red;
  font-size: 0.85rem;
}

.submit-btn {
  --color: #ffffff;
  font-family: inherit;
  display: inline-block;
  background: #000000;
  width: 8em;
  height: 2.6em;
  line-height: 2.5em;
  margin: 10px;
  position: relative;
  cursor: pointer;
  overflow: hidden;
  border: 2px solid var(--color);
  transition: color 0.5s;
  z-index: 1;
  font-size: 1rem;
  border-radius: 6px;
  font-weight: 500;
  color: var(--color);
  text-align: center;
  text-decoration: none;
}

.submit-btn::before {
  content: "";
  position: absolute;
  z-index: -1;
  background: var(--color);
  height: 150px;
  width: 200px;
  border-radius: 50%;
  top: 100%;
  left: 100%;
  transition: all 0.7s;
}

.submit-btn:hover {
  color: #8ca1c6;
}

.submit-btn:hover::before {
  top: -1.875rem;
  left: -1.875rem;
}

.submit-btn:active::before {
  background: #3a0ca3;
  transition: background 0s;
}

.contact-info {
  display: flex;
  flex-direction: column;
  align-items: center; 
}

.contact-info p {
  width: 17rem;
  text-align: left; 
}

</style>