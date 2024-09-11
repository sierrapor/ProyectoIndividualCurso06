<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

const astros = ref([]);
const router = useRouter();
const showModal = ref(false);
const astroToDelete = ref(null);

const fetchAstros = async () => {
  try {
    const response = await axios.get('/api/astros');
    astros.value = response.data;
  } catch (error) {
    console.error('Error fetching astros:', error);
  }
};

const confirmDeleteAstro = (id) => {
  astroToDelete.value = id;
  showModal.value = true;
};

const deleteAstro = async () => {
  try {
    await axios.delete(`/api/astros/${astroToDelete.value}`);
    astros.value = astros.value.filter(astro => astro.id !== astroToDelete.value);
    showModal.value = false;
    astroToDelete.value = null;
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

const editAstro = (id) => {
  router.push(`/astrosForm/${id}?edit=true`);
};

const formatNumber = (number) => {
  return number.toLocaleString('es-ES');
};

onMounted(() => {
  fetchAstros();
});
</script>

<<template>
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
          <th class="actions-column">Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="astro in astros" :key="astro.id">
          <td>{{ astro.nombre }}</td>
          <td>{{ formatNumber(astro.masa) }}</td>
          <td>{{ formatNumber(astro.densidad) }}</td>
          <td>{{ formatNumber(astro.distancia) }}</td>
          <td>{{ formatNumber(astro.magnitudAparente) }}</td>
          <td>{{ formatNumber(astro.corrimientoAlRojo) }}</td>
          <td>{{ formatNumber(astro.temperatura) }}</td>
          <td>{{ formatNumber(astro.edad) }}</td>
          <td>{{ astro.tipo.nombre }}</td>
          <td class="actions-column">
            <button @click="viewAstro(astro.id)" class="view-button">Ver</button>
            <button @click="editAstro(astro.id)" class="edit-button">Actualizar</button>
            <button @click="confirmDeleteAstro(astro.id)" class="delete-button">Borrar</button>
          </td>
        </tr>
      </tbody>
    </table>

    <div v-if="showModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="showModal = false">&times;</span>
        <p>¿Estás seguro de que deseas borrar este astro?</p>
        <button @click="deleteAstro" class="confirm-button">Sí</button>
        <button @click="showModal = false" class="cancel-button">No</button>
      </div>
    </div>
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