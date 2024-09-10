<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const tipos = ref([]);
const router = useRouter();

const fetchTipos = async () => {
  try {
    const response = await axios.get('/api/tipos');
    tipos.value = response.data;
  } catch (error) {
    console.error('Error fetching tipos:', error);
  }
};

const deleteTipo = async (id) => {
  try {
    await axios.delete(`/api/tipos/${id}`);
    tipos.value = tipos.value.filter(tipo => tipo.id !== id);
  } catch (error) {
    console.error('Error deleting tipo:', error);
  }
};

const goToCreateForm = () => {
  router.push('/tiposForm');
};

const viewTipo = (id) => {
  router.push(`/tiposForm/${id}`);
};

const editTipo = (id) => {
  router.push(`/tiposForm/${id}?edit=true`);
};

onMounted(() => {
  fetchTipos();
});
</script>

<template>
    <div>
      <h1>Tipos</h1>
      <button @click="goToCreateForm">Crear</button>
      <table>
        <thead>
          <tr>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>Luminoso</th>
            <th>Orbitante</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="tipo in tipos" :key="tipo.id">
            <td>{{ tipo.nombre }}</td>
            <td>{{ tipo.descripcion }}</td>
            <td>{{ tipo.luminoso ? 'Sí' : 'No' }}</td>
            <td>{{ tipo.orbitante ? 'Sí' : 'No' }}</td>
            <td>
              <button @click="viewTipo(tipo.id)">Ver</button>
              <button @click="editTipo(tipo.id)">Actualizar</button>
              <button @click="deleteTipo(tipo.id)">Borrar</button>
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
}
</style>