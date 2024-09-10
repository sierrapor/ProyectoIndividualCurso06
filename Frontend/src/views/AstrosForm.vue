<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { useRouter, useRoute } from 'vue-router';

const astro = ref({
  nombre: '',
  descripcion: '',
  masa: 0,
  densidad: 0,
  distancia: 0,
  magnitudAparente: 0,
  corrimientoAlRojo: 0,
  temperatura: 0,
  edad: 0,
  tipo: {
    id: null
  }
});

const tipos = ref([]);
const isViewMode = ref(false);
const isEditMode = ref(false);
const router = useRouter();
const route = useRoute();

const fetchTipos = async () => {
  try {
    const response = await axios.get('/api/tipos');
    tipos.value = response.data;
  } catch (error) {
    console.error('Error fetching tipos:', error);
  }
};

const fetchAstro = async (id) => {
  try {
    const response = await axios.get(`/api/astros/${id}`);
    astro.value = response.data;
  } catch (error) {
    console.error('Error fetching astro:', error);
  }
};

const submitForm = async () => {
  try {
    if (isEditMode.value) {
      await axios.put(`/api/astros/${route.params.id}`, astro.value);
    } else {
      await axios.post('/api/astros', astro.value);
    }
    router.push('/astros');
  } catch (error) {
    console.error('Error submitting form:', error);
  }
};

onMounted(() => {
  fetchTipos();
  if (route.params.id) {
    if (route.query.edit) {
      isEditMode.value = true;
    } else {
      isViewMode.value = true;
    }
    fetchAstro(route.params.id);
  }
});
</script>

<template>
    <div class="form-container">
      <h1>{{ isViewMode ? 'Ver Astro' : isEditMode ? 'Actualizar Astro' : 'Crear Astro' }}</h1>
      <form @submit.prevent="submitForm" v-if="!isViewMode">
        <div class="form-group">
          <label for="nombre">Nombre:</label>
          <input type="text" id="nombre" v-model="astro.nombre" required />
        </div>
        <div class="form-group">
          <label for="descripcion">Descripción:</label>
          <input type="text" id="descripcion" v-model="astro.descripcion" required />
        </div>
        <div class="form-group">
          <label for="masa">Masa:</label>
          <input type="number" id="masa" v-model="astro.masa" required />
        </div>
        <div class="form-group">
          <label for="densidad">Densidad:</label>
          <input type="number" id="densidad" v-model="astro.densidad" required />
        </div>
        <div class="form-group">
          <label for="distancia">Distancia:</label>
          <input type="number" id="distancia" v-model="astro.distancia" required />
        </div>
        <div class="form-group">
          <label for="magnitudAparente">Magnitud Aparente:</label>
          <input type="number" id="magnitudAparente" v-model="astro.magnitudAparente" required />
        </div>
        <div class="form-group">
          <label for="corrimientoAlRojo">Corrimiento al Rojo:</label>
          <input type="number" id="corrimientoAlRojo" v-model="astro.corrimientoAlRojo" required />
        </div>
        <div class="form-group">
          <label for="temperatura">Temperatura:</label>
          <input type="number" id="temperatura" v-model="astro.temperatura" required />
        </div>
        <div class="form-group">
          <label for="edad">Edad:</label>
          <input type="number" id="edad" v-model="astro.edad" required />
        </div>
        <div class="form-group">
          <label for="tipo">Tipo:</label>
          <select id="tipo" v-model="astro.tipo.id" required>
            <option v-for="tipo in tipos" :key="tipo.id" :value="tipo.id">{{ tipo.nombre }}</option>
          </select>
        </div>
        <button type="submit" class="submit-button">{{ isEditMode ? 'Actualizar' : 'Crear' }}</button>
      </form>
      <div v-else>
        <div class="form-group">
          <label>Nombre:</label>
          <p>{{ astro.nombre }}</p>
        </div>
        <div class="form-group">
          <label>Descripción:</label>
          <p>{{ astro.descripcion }}</p>
        </div>
        <div class="form-group">
          <label>Masa:</label>
          <p>{{ astro.masa }}</p>
        </div>
        <div class="form-group">
          <label>Densidad:</label>
          <p>{{ astro.densidad }}</p>
        </div>
        <div class="form-group">
          <label>Distancia:</label>
          <p>{{ astro.distancia }}</p>
        </div>
        <div class="form-group">
          <label>Magnitud Aparente:</label>
          <p>{{ astro.magnitudAparente }}</p>
        </div>
        <div class="form-group">
          <label>Corrimiento al Rojo:</label>
          <p>{{ astro.corrimientoAlRojo }}</p>
        </div>
        <div class="form-group">
          <label>Temperatura:</label>
          <p>{{ astro.temperatura }}</p>
        </div>
        <div class="form-group">
          <label>Edad:</label>
          <p>{{ astro.edad }}</p>
        </div>
        <div class="form-group">
          <label>Tipo:</label>
          <p>{{ astro.tipo.nombre }}</p>
        </div>
        <button @click="router.push('/astros')" class="submit-button">Volver</button>
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
input[type="number"],
select {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
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