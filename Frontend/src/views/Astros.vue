<script setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import axios from 'axios';
import Notification from '@/components/Notification.vue';

const astros = ref([]);
const router = useRouter();
const route = useRoute();
const showModal = ref(false);
const astroToDelete = ref(null);
const astroToDeleteName = ref('');
const notificationMessage = ref('');
const notificationType = ref('success');

const fetchAstros = async () => {
  try {
    const response = await axios.get('/api/astros');
    astros.value = response.data;
  } catch (error) {
    console.error('Error fetching astros:', error);
  }
};

const confirmDeleteAstro = (id) => {
  const astro = astros.value.find(a => a.id === id);
  if (astro) {
    astroToDelete.value = id;
    astroToDeleteName.value = astro.nombre;
    showModal.value = true;
  }
};

const deleteAstro = async () => {
  try {
    // Actualiza la lista de astros antes de proceder con la eliminación
    await fetchAstros();

    // Verifica si el astro aún existe en la lista actualizada
    const astro = astros.value.find(a => a.id === astroToDelete.value);
    if (!astro) {
      notificationMessage.value = `El astro "${astroToDeleteName.value}" ya no existe.`;
      notificationType.value = 'error';
      showModal.value = false;
      return;
    }

    // Procede con la eliminación del astro
    await axios.delete(`/api/astros/${astroToDelete.value}`);
    astros.value = astros.value.filter(astro => astro.id !== astroToDelete.value);
    showModal.value = false;
    notificationMessage.value = `El astro "${astroToDeleteName.value}" ha sido borrado correctamente.`;
    notificationType.value = 'success';
    astroToDelete.value = null;
    astroToDeleteName.value = '';
  } catch (error) {
    console.error('Error deleting astro:', error);
    notificationMessage.value = 'Error al eliminar el astro';
    notificationType.value = 'error';
  }
};

const goToCreateForm = () => {
  router.push('/astrosForm');
};

const viewAstro = (id) => {
  router.push(`/astrosForm/${id}`);
};

const editAstro = (id) => {
  router.push(`/astrosForm/${id}?edit=true`);
};

const formatNumber = (number) => {
  return number.toLocaleString('es-ES');
};

onMounted(() => {
  fetchAstros();
  if (route.query.message) {
    notificationMessage.value = route.query.message;
    notificationType.value = route.query.type || 'success';
  }
});
</script>

<template>
  <div>
    <div class="header-container">
      <h1>Astros</h1>
      <button @click="goToCreateForm" class="create-button">Crear</button>
    </div>
    <div class="table-container">
      <table>
        <thead>
          <tr>
            <th>Nombre</th>
            <th>Masa<br><small>(kg)</small></th>
            <th>Densidad<br><small>(kg/m<sup>3</sup>)</small></th>
            <th>Distancia<br><small>(años luz)</small></th>
            <th>m<sub>a</sub><br><small>(adimensional)</small></th>
            <th><i>z</i><sub>redshift</sub><br><small>(adimensional)</small></th>
            <th>Temperatura<br><small>(K)</small></th>
            <th>Edad<br><small>(millones de años)</small></th>
            <th>Tipo</th>
            <th class="actions-column">Acciones</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="astro in astros" :key="astro.id">
            <td>{{ astro.nombre }}</td>
            <td class="text-right">{{ formatNumber(astro.masa) }}</td>
            <td class="text-right">{{ formatNumber(astro.densidad) }}</td>
            <td class="text-right">{{ formatNumber(astro.distancia) }}</td>
            <td class="text-right">{{ formatNumber(astro.magnitudAparente) }}</td>
            <td class="text-right">{{ formatNumber(astro.corrimientoAlRojo) }}</td>
            <td class="text-right">{{ formatNumber(astro.temperatura) }}</td>
            <td class="text-right">{{ formatNumber(astro.edad) }}</td>
            <td>{{ astro.tipo.nombre }}</td>
            <td class="actions-column">
              <button @click="viewAstro(astro.id)" class="view-button">Ver</button>
              <button @click="editAstro(astro.id)" class="edit-button">Editar</button>
              <button @click="confirmDeleteAstro(astro.id)" class="delete-button">Borrar</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <div v-if="showModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="showModal = false">&times;</span>
        <p>¿Estás seguro de que deseas borrar el astro "{{ astroToDeleteName }}"?</p>
        <button @click="deleteAstro" class="confirm-button">Sí</button>
        <button @click="showModal = false" class="cancel-button">No</button>
      </div>
    </div>

    <Notification v-if="notificationMessage" :message="notificationMessage" :type="notificationType" />
  </div>
</template>

<style scoped>
.header-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 20px;
}

.table-container {
  max-height: 650px; /* Aumenta la altura según tus necesidades */
  overflow-y: auto;
  position: relative;
}

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
  position: sticky;
  top: 0;
  z-index: 1;
}

.text-right {
  text-align: right;
}

button {
  margin-right: 5px;
  color: white;
  border: none;
  padding: 5px 10px;
  cursor: pointer;
  border-radius: 4px;
  width: 70px; /* Define un ancho fijo para todos los botones */
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
  margin-top: 10px;
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

.modal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0, 0, 0, 0.4);
}

.modal-content {
  background-color: #fefefe;
  padding: 20px;
  border: 1px solid #888;
  width: 300px;
  border-radius: 8px;
  text-align: center;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

.confirm-button {
  background-color: #4CAF50;
  color: white;
  border: none;
  padding: 10px 20px;
  margin: 10px;
  cursor: pointer;
  border-radius: 4px;
}

.confirm-button:hover {
  background-color: #45a049;
}

.cancel-button {
  background-color: #f44336;
  color: white;
  border: none;
  padding: 10px 20px;
  margin: 10px;
  cursor: pointer;
  border-radius: 4px;
}

.cancel-button:hover {
  background-color: #da190b;
}
</style>