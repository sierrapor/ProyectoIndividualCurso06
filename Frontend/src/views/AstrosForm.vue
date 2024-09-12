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
const errors = ref({});
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

const convertCommasToDots = () => {
  const removeThousandSeparators = (value) => value.toString().replace(/\./g, '').replace(',', '.');

  astro.value.masa = removeThousandSeparators(astro.value.masa);
  astro.value.densidad = removeThousandSeparators(astro.value.densidad);
  astro.value.distancia = removeThousandSeparators(astro.value.distancia);
  astro.value.magnitudAparente = removeThousandSeparators(astro.value.magnitudAparente);
  astro.value.corrimientoAlRojo = removeThousandSeparators(astro.value.corrimientoAlRojo);
  astro.value.temperatura = removeThousandSeparators(astro.value.temperatura);
  astro.value.edad = removeThousandSeparators(astro.value.edad);
};

const validateForm = () => {
  errors.value = {};
  if (!astro.value.nombre || astro.value.nombre.length < 1 || astro.value.nombre.length > 50) {
    errors.value.nombre = 'El nombre debe tener entre 1 y 50 caracteres.';
  }
  if (!astro.value.descripcion || astro.value.descripcion.length < 1 || astro.value.descripcion.length > 500) {
    errors.value.descripcion = 'La descripción debe tener entre 1 y 500 caracteres.';
  }
  if (astro.value.masa <= 0) {
    errors.value.masa = 'La masa debe ser un número positivo.';
  }
  if (astro.value.densidad <= 0) {
    errors.value.densidad = 'La densidad debe ser un número positivo.';
  }
  if (astro.value.distancia <= 0) {
    errors.value.distancia = 'La distancia debe ser un número positivo.';
  }
  if (astro.value.corrimientoAlRojo < 0) {
    errors.value.corrimientoAlRojo = 'El corrimiento al rojo debe ser un número positivo o cero.';
  }
  if (astro.value.temperatura <= 0) {
    errors.value.temperatura = 'La temperatura debe ser un número positivo.';
  }
  if (astro.value.edad <= 0) {
    errors.value.edad = 'La edad debe ser un número positivo.';
  }
  if (!astro.value.tipo.id) {
    errors.value.tipo = 'Debe seleccionar un tipo.';
  }
  return Object.keys(errors.value).length === 0;
};

const submitForm = async () => {
  if (!validateForm()) {
    return;
  }
  try {
    convertCommasToDots();
    if (isEditMode.value) {
      await axios.put(`/api/astros/${route.params.id}`, astro.value);
      router.push({ path: '/astros', query: { message: `El astro "${astro.value.nombre}" ha sido actualizado correctamente.`, type: 'success' } });
    } else {
      await axios.post('/api/astros', astro.value);
      router.push({ path: '/astros', query: { message: `El astro "${astro.value.nombre}" ha sido creado correctamente.`, type: 'success' } });
    }
  } catch (error) {
    console.error('Error submitting form:', error);
  }
};

const cancel = () => {
  router.push('/astros');
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
    <h1>{{ isViewMode ? 'Ver Astro' : isEditMode ? 'Editar Astro' : 'Crear Astro' }}</h1>
    <form @submit.prevent="submitForm" v-if="!isViewMode">
      <div class="form-grid">
        <div class="form-group">
          <label for="nombre">Nombre:</label>
          <input type="text" id="nombre" v-model="astro.nombre" required />
          <span v-if="errors.nombre" class="error-message">{{ errors.nombre }}</span>
        </div>
        <div class="form-group">
          <label for="descripcion">Descripción:</label>
          <input type="text" id="descripcion" v-model="astro.descripcion" required />
          <span v-if="errors.descripcion" class="error-message">{{ errors.descripcion }}</span>
        </div>
        <div class="form-group">
          <label for="masa">Masa (kg):</label>
          <input type="text" id="masa" v-model="astro.masa" inputmode="decimal" required />
          <span v-if="errors.masa" class="error-message">{{ errors.masa }}</span>
        </div>
        <div class="form-group">
          <label for="densidad">Densidad (kg/m<sup>3</sup>):</label>
          <input type="text" id="densidad" v-model="astro.densidad" inputmode="decimal" required />
          <span v-if="errors.densidad" class="error-message">{{ errors.densidad }}</span>
        </div>
        <div class="form-group">
          <label for="distancia">Distancia (años luz):</label>
          <input type="text" id="distancia" v-model="astro.distancia" inputmode="decimal" required />
          <span v-if="errors.distancia" class="error-message">{{ errors.distancia }}</span>
        </div>
        <div class="form-group">
          <label for="magnitudAparente">Magnitud Aparente (adimensional):</label>
          <input type="text" id="magnitudAparente" v-model="astro.magnitudAparente" inputmode="decimal" required />
          <span v-if="errors.magnitudAparente" class="error-message">{{ errors.magnitudAparente }}</span>
        </div>
        <div class="form-group">
          <label for="corrimientoAlRojo">Corrimiento al Rojo (adimensional):</label>
          <input type="text" id="corrimientoAlRojo" v-model="astro.corrimientoAlRojo" inputmode="decimal" required />
          <span v-if="errors.corrimientoAlRojo" class="error-message">{{ errors.corrimientoAlRojo }}</span>
        </div>
        <div class="form-group">
          <label for="temperatura">Temperatura (K):</label>
          <input type="text" id="temperatura" v-model="astro.temperatura" inputmode="decimal" required />
          <span v-if="errors.temperatura" class="error-message">{{ errors.temperatura }}</span>
        </div>
        <div class="form-group">
          <label for="edad">Edad (millones de años):</label>
          <input type="text" id="edad" v-model="astro.edad" inputmode="decimal" required />
          <span v-if="errors.edad" class="error-message">{{ errors.edad }}</span>
        </div>
        <div class="form-group">
          <label for="tipo">Tipo:</label>
          <select id="tipo" v-model="astro.tipo.id" required>
            <option v-for="tipo in tipos" :key="tipo.id" :value="tipo.id">{{ tipo.nombre }}</option>
          </select>
          <span v-if="errors.tipo" class="error-message">{{ errors.tipo }}</span>
        </div>
      </div>
      <div class="button-group">
        <button type="submit" class="submit-button">{{ 'Guardar' }}</button>
        <button type="button" @click="cancel" class="cancel-button">Cancelar</button>
      </div>
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
        <label>Masa (kg):</label>
        <p>{{ astro.masa }}</p>
      </div>
      <div class="form-group">
        <label>Densidad (kg/m<sup>3</sup>):</label>
        <p>{{ astro.densidad }}</p>
      </div>
      <div class="form-group">
        <label>Distancia (años luz):</label>
        <p>{{ astro.distancia }}</p>
      </div>
      <div class="form-group">
        <label>Magnitud Aparente (adimensional):</label>
        <p>{{ astro.magnitudAparente }}</p>
      </div>
      <div class="form-group">
        <label>Corrimiento al Rojo (adimensional):</label>
        <p>{{ astro.corrimientoAlRojo }}</p>
      </div>
      <div class="form-group">
        <label>Temperatura (K):</label>
        <p>{{ astro.temperatura }}</p>
      </div>
      <div class="form-group">
        <label>Edad (millones de años):</label>
        <p>{{ astro.edad }}</p>
      </div>
      <div class="form-group">
        <label>Tipo:</label>
        <p>{{ astro.tipo.nombre }}</p>
      </div>
      <div class="button-group">
        <button class="submit-button disabled-button" disabled>Guardar</button>
        <button @click="cancel" class="cancel-button">Atrás</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.form-container {
  max-width: 800px; /* Aumenta el ancho para acomodar dos columnas */
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

input[type="text"] {
  text-align: right; /* Alinea el texto a la derecha por defecto */
}

input#nombre,
input#descripcion {
  text-align: left; /* Alinea el texto a la izquierda para los campos nombre y descripción */
}

input[type="number"] {
  text-align: right; /* Alinea el texto a la derecha */
}

.button-group {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
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

.error-message {
  color: red;
  font-size: 0.875em;
  margin-top: 5px;
}

/* Estilos para el diseño de dos columnas */
.form-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
}

.form-grid .form-group {
  margin-bottom: 15px;
}
</style>