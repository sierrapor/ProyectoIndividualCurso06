<script setup>
import { ref, watch, onMounted, onBeforeUnmount } from 'vue';

const props = defineProps({
  message: String,
  type: {
    type: String,
    default: 'success'
  },
  duration: {
    type: Number,
    default: 5000 // 5 seconds
  }
});

const visible = ref(true);
let timeoutId = null;

const startTimer = () => {
  clearTimeout(timeoutId);
  timeoutId = setTimeout(() => {
    visible.value = false;
  }, props.duration);
};

watch(() => props.message, () => {
  visible.value = true;
  startTimer();
});

onMounted(() => {
  startTimer();
});

onBeforeUnmount(() => {
  clearTimeout(timeoutId);
});
</script>

<template>
  <transition name="fade">
    <div v-if="visible" class="notification" :class="type">
      {{ message }}
    </div>
  </transition>
</template>
  
<style scoped>
.notification {
  position: fixed;
  top: 85px;
  right: 20px;
  padding: 15px;
  border-radius: 5px;
  color: white;
  z-index: 1000;
}

.notification.success {
  background-color: #4CAF50;
}

.notification.error {
  background-color: #f44336;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s;
}

.fade-enter, .fade-leave-to /* .fade-leave-active in <2.1.8 */ {
  opacity: 0;
}
</style>