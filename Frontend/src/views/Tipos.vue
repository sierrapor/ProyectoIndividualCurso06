<script setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import axios from 'axios';
import Notification from '@/components/Notification.vue';

const tipos = ref([]);
const router = useRouter();
const route = useRoute();
const showModal = ref(false);
const showErrorModal = ref(false);
const tipoToDeleteId = ref(null);
const tipoToDeleteName = ref('');
const tipoToDelete = ref(null); // Definir tipoToDelete
const notificationMessage = ref('');
const notificationType = ref('');

import checkMarkIcon from '@/assets/Check-mark.png';
import redCrossIcon from '@/assets/RedCross.png';

const fetchTipos = async () => {
  try {
    const response = await axios.get('/api/tipos');
    tipos.value = response.data;
  } catch (error) {
    console.error('Error fetching tipos:', error);
  }
};

const goToCreateForm = () => {
  router.push({ path: '/tiposForm' });
};

const viewTipo = (id) => {
  router.push({ path: `/tiposForm/${id}`, query: { view: true } });
};

const editTipo = (id) => {
  router.push({ path: `/tiposForm/${id}`, query: { edit: true } });
};

const confirmDeleteTipo = (id) => {
  const tipo = tipos.value.find(t => t.id === id);
  tipoToDeleteId.value = id;
  tipoToDeleteName.value = tipo.nombre;
  tipoToDelete.value = tipo; // Inicializar tipoToDelete
  showModal.value = true;
};

const deleteTipo = async () => {
  try {
    // Verificar si el tipo tiene astros asociados
    if (tipoToDelete.value.astros && tipoToDelete.value.astros.length > 0) {
      notificationMessage.value = `El tipo "${tipoToDeleteName.value}" no se puede eliminar porque tiene astros asociados.`;
      notificationType.value = 'error';
      showErrorModal.value = true;
      return;
    }

    // Si no tiene astros asociados, proceder con la eliminación
    await axios.delete(`/api/tipos/${tipoToDeleteId.value}`);
    tipos.value = tipos.value.filter(t => t.id !== tipoToDeleteId.value);
    notificationMessage.value = `El tipo "${tipoToDeleteName.value}" ha sido borrado correctamente.`;
    notificationType.value = 'success';
  } catch (error) {
    if (error.response && error.response.status === 400) {
      showErrorModal.value = true;
    } else {
      console.error('Error deleting tipo:', error);
      notificationMessage.value = 'Error al eliminar el tipo';
      notificationType.value = 'error';
    }
  } finally {
    showModal.value = false;
  }
};

onMounted(() => {
  fetchTipos();
  if (route.query.message) {
    notificationMessage.value = route.query.message;
    notificationType.value = route.query.type || 'success';
  }
});
</script>

<template>
  <div>
    <div class="header-container">
      <h1>Tipos de Objetos Astronómicos</h1>
      <button @click="goToCreateForm" class="create-button">Crear</button>
    </div>
    <div class="table-container">
      <table>
        <thead>
          <tr>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>Luminoso</th>
            <th>Orbitante</th>
            <th class="actions-column"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="tipo in tipos" :key="tipo.id">
            <td>{{ tipo.nombre }}</td>
            <td>{{ tipo.descripcion }}</td>
            <td>
              {{ tipo.luminoso ? 'Sí' : 'No' }}
              <img :src="tipo.luminoso ? checkMarkIcon : redCrossIcon" alt="Luminoso Icon" class="boolean-icon" />
            </td>
            <td>
              {{ tipo.orbitante ? 'Sí' : 'No' }}
              <img :src="tipo.orbitante ? checkMarkIcon : redCrossIcon" alt="Orbitante Icon" class="boolean-icon" />
            </td>
            <td class="actions-column">
              <button @click="viewTipo(tipo.id)" class="view-button">Ver</button>
              <button @click="editTipo(tipo.id)" class="edit-button">Editar</button>
              <button @click="confirmDeleteTipo(tipo.id)" class="delete-button">Borrar</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <div v-if="showModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="showModal = false">&times;</span>
        <p>¿Estás seguro de que deseas borrar el tipo "{{ tipoToDeleteName }}"?</p>
        <button @click="deleteTipo" class="confirm-button">Sí</button>
        <button @click="showModal = false" class="cancel-button">No</button>
      </div>
    </div>

    <div v-if="showErrorModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="showErrorModal = false">&times;</span>
        <p>No se puede borrar el tipo "{{ tipoToDeleteName }}" porque tiene astros asociados.</p>
        <button @click="showErrorModal = false" class="cancel-button">Cerrar</button>
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

.boolean-icon {
  width: 20px;
  height: 20px;
  margin-left: 5px;
  vertical-align: middle;
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