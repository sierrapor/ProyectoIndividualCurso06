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
    <button @click="goToCreateForm" class="create-button">Crear</button>
    <table>
      <thead>
        <tr>
          <th>Nombre</th>
          <th>Descripción</th>
          <th>Luminoso</th>
          <th>Orbitante</th>
          <th class="actions-column">Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="tipo in tipos" :key="tipo.id">
          <td>{{ tipo.nombre }}</td>
          <td>{{ tipo.descripcion }}</td>
          <td>{{ tipo.luminoso ? 'Sí' : 'No' }}</td>
          <td>{{ tipo.orbitante ? 'Sí' : 'No' }}</td>
          <td class="actions-column">
            <button @click="viewTipo(tipo.id)" class="view-button">Ver</button>
            <button @click="editTipo(tipo.id)" class="edit-button">Actualizar</button>
            <button @click="deleteTipo(tipo.id)" class="delete-button">Borrar</button>
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
  color: white;
  border: none;
  padding: 5px 10px;
  cursor: pointer;
  border-radius: 4px;
}

button:hover {
  background-color: #ff1a1a;
}

button.view-button {
  background-color: #4d79ff;
}

button.view-button:hover {
  background-color: #1a53ff;
}

button.edit-button {
  background-color: #4d79ff;
}

button.edit-button:hover {
  background-color: #1a53ff;
}

button.delete-button {
  background-color: #ff4d4d;
}

button.delete-button:hover {
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

.actions-column {
  width: 150px; /* Ajusta este valor según el ancho de tus botones */
  white-space: nowrap;
}
</style>