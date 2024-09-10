<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter, useRoute } from 'vue-router';

const tipo = ref({
  nombre: '',
  descripcion: '',
  luminoso: false,
  orbitante: false
});

const isViewMode = ref(false);
const router = useRouter();
const route = useRoute();

const fetchTipo = async (id) => {
  try {
    const response = await axios.get(`/api/tipos/${id}`);
    tipo.value = response.data;
  } catch (error) {
    console.error('Error fetching tipo:', error);
  }
};

const submitForm = async () => {
  try {
    await axios.post('/api/tipos', tipo.value);
    router.push('/tipos');
  } catch (error) {
    console.error('Error creating tipo:', error);
  }
};

onMounted(() => {
  if (route.params.id) {
    isViewMode.value = true;
    fetchTipo(route.params.id);
  }
});
</script>

<template>
    <div class="form-container">
      <h1>{{ isViewMode ? 'Ver Tipo' : 'Crear Tipo' }}</h1>
      <form @submit.prevent="submitForm" v-if="!isViewMode">
        <div class="form-group">
          <label for="nombre">Nombre:</label>
          <input type="text" id="nombre" v-model="tipo.nombre" required />
        </div>
        <div class="form-group">
          <label for="descripcion">Descripción:</label>
          <input type="text" id="descripcion" v-model="tipo.descripcion" required />
        </div>
        <div class="form-group">
          <label for="luminoso">Luminoso:</label>
          <input type="checkbox" id="luminoso" v-model="tipo.luminoso" />
        </div>
        <div class="form-group">
          <label for="orbitante">Orbitante:</label>
          <input type="checkbox" id="orbitante" v-model="tipo.orbitante" />
        </div>
        <button type="submit" class="submit-button">Crear</button>
      </form>
      <div v-else>
        <div class="form-group">
          <label>Nombre:</label>
          <p>{{ tipo.nombre }}</p>
        </div>
        <div class="form-group">
          <label>Descripción:</label>
          <p>{{ tipo.descripcion }}</p>
        </div>
        <div class="form-group">
          <label>Luminoso:</label>
          <p>{{ tipo.luminoso ? 'Sí' : 'No' }}</p>
        </div>
        <div class="form-group">
          <label>Orbitante:</label>
          <p>{{ tipo.orbitante ? 'Sí' : 'No' }}</p>
        </div>
        <button @click="router.push('/tipos')" class="submit-button">Volver</button>
      </div>
    </div>
</template>

<style scoped>
.form-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
}

h1 {
  text-align: center;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input[type="text"],
input[type="checkbox"] {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}

input[type="checkbox"] {
  width: auto;
}

.submit-button {
  display: block;
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.submit-button:hover {
  background-color: #0056b3;
}
</style>