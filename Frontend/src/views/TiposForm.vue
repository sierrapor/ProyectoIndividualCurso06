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

const astros = ref([]);
const isViewMode = ref(false);
const isEditMode = ref(false);
const router = useRouter();
const route = useRoute();

const fetchTipo = async (id) => {
  try {
    const response = await axios.get(`/api/tipos/${id}`);
    tipo.value = response.data;
    astros.value = response.data.astros; // Extraer los astros de la respuesta
  } catch (error) {
    console.error('Error fetching tipo:', error);
  }
};

const submitForm = async () => {
  try {
    if (isEditMode.value) {
      await axios.put(`/api/tipos/${route.params.id}`, tipo.value);
      router.push({ path: '/tipos', query: { message: `El tipo "${tipo.value.nombre}" ha sido editado correctamente.`, type: 'success' } });
    } else {
      await axios.post('/api/tipos', tipo.value);
      router.push({ path: '/tipos', query: { message: `El tipo "${tipo.value.nombre}" ha sido creado correctamente.`, type: 'success' } });
    }
  } catch (error) {
    console.error('Error submitting form:', error);
  }
};

const cancel = () => {
  router.push('/tipos');
};

onMounted(() => {
  if (route.params.id) {
    if (route.query.edit) {
      isEditMode.value = true;
    } else {
      isViewMode.value = true;
    }
    fetchTipo(route.params.id);
  }
});
</script>

<template>
  <div class="form-container">
    <h1>{{ isViewMode ? 'Ver Tipo' : isEditMode ? 'Editar Tipo' : 'Crear Tipo' }}</h1>
    <form @submit.prevent="submitForm" v-if="!isViewMode || isEditMode">
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
      <div class="form-group">
        <label>Astros:</label>
        <ul v-if="astros.length">
          <li v-for="astro in astros" :key="astro.id">{{ astro.nombre }}</li>
        </ul>
        <p v-if="!astros.length">No hay astros asociados</p>
      </div>
      <div class="form-group actions-column">
        <button type="submit" class="submit-button">{{ 'Guardar' }}</button>
        <button type="button" @click="cancel" class="cancel-button">Cancelar</button>
      </div>
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
      <div class="form-group">
        <label>Astros:</label>
        <div class="astros-list">
          <ul>
            <li v-for="astro in astros" :key="astro.id">{{ astro.nombre }}</li>
          </ul>
        </div>
        <p v-if="!astros.length">No hay astros asociados</p>
      </div>
      <div class="form-group actions-column">
        <button class="submit-button disabled-button" disabled>Guardar</button>
        <button @click="cancel" class="cancel-button">Atrás</button>
      </div>
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
  accent-color: #0056b3; /* Cambia el color del checkbox a un azul más oscuro */
}

.submit-button,
.cancel-button {
  display: inline-block;
  width: 48%;
  padding: 10px;
  margin-top: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  text-align: center;
}

.submit-button:hover,
.cancel-button:hover {
  background-color: #0056b3;
}

.cancel-button {
  background-color: #6c757d;
}

.cancel-button:hover {
  background-color: #5a6268;
}

.disabled-button {
  background-color: #d3d3d3;
  cursor: not-allowed;
}

.astros-list {
  max-height: 200px; /* Ajusta la altura según sea necesario */
  overflow-y: auto;
}

.astros-list ul {
  display: grid;
  grid-template-columns: 1fr 1fr; /* Dos columnas */
  gap: 10px;
  list-style-type: none;
  padding: 0;
}

.astros-list li {
  background-color: #f1f1f1;
  padding: 5px;
  border-radius: 4px;
}
</style>