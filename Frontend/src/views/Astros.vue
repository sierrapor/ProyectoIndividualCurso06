<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const astros = ref([]);
const router = useRouter();

const fetchAstros = async () => {
  try {
    const response = await axios.get('/api/astros');
    astros.value = response.data;
  } catch (error) {
    console.error('Error fetching astros:', error);
  }
};

const deleteAstro = async (id) => {
  try {
    await axios.delete(`/api/astros/${id}`);
    astros.value = astros.value.filter(astro => astro.id !== id);
  } catch (error) {
    console.error('Error deleting astro:', error);
  }
};

const goToCreateForm = () => {
  router.push('/astrosForm');
};

const viewAstro = (id) => {
  router.push(`/astrosForm/${id}`);
};

onMounted(() => {
  fetchAstros();
});
</script>

<template>
    <div>
      <h1>Astros</h1>
      <button @click="goToCreateForm" class="create-button">Crear</button>
      <table>
        <thead>
          <tr>
            <th>Nombre</th>
            <th>Masa<br><small>(kg)</small></th>
            <th>Densidad<br><small>(kg/m<sup>3</sup>)</small></th>
            <th>Distancia<br><small>(años luz)</small></th>
            <th>m<sub>a</sub><br><small>(dimensionless)</small></th>
            <th><i>z</i><sub>redshift</sub><br><small>(dimensionless)</small></th>
            <th>Temperatura<br><small>(K)</small></th>
            <th>Edad<br><small>(millones de años)</small></th>
            <th>Tipo</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="astro in astros" :key="astro.id">
            <td>{{ astro.nombre }}</td>
            <td>{{ astro.masa }}</td>
            <td>{{ astro.densidad }}</td>
            <td>{{ astro.distancia }}</td>
            <td>{{ astro.magnitudAparente }}</td>
            <td>{{ astro.corrimientoAlRojo }}</td>
            <td>{{ astro.temperatura }}</td>
            <td>{{ astro.edad }}</td>
            <td>{{ astro.tipo.nombre }}</td>
            <td>
              <button @click="viewAstro(astro.id)">Ver</button>
              <button @click="deleteAstro(astro.id)">Borrar</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
</template>

<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
}

th {
  background-color: #f2f2f2;
}

button {
  margin-right: 5px;
  background-color: #ff4d4d;
  color: white;
  border: none;
  padding: 5px 10px;
  cursor: pointer;
  border-radius: 4px;
}

button:hover {
  background-color: #ff1a1a;
}

.create-button {
  background-color: #4CAF50;
  color: white;
  border: none;
  padding: 10px 20px;
  margin-bottom: 20px;
  cursor: pointer;
  border-radius: 4px;
}

.create-button:hover {
  background-color: #45a049;
}
</style>