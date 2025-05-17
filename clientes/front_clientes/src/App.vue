<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";

const clientes = ref([]);
const nuevoCliente = ref({
  nombre: '',
  apellidos: '',
  dni: '',
  telefono: '',
  email: ''
});

const clienteEditando = ref(null);

// Función para cargar clientes desde el backend
const cargarClientes = async () => {
  const response = await axios.get('http://localhost:8080/clientes/traerClientes');
  clientes.value = response.data;
  console.log('Clientes cargados');
};

// Ejecutar al montar el componente
onMounted(cargarClientes);

// Función para agregar o actualizar cliente
const agregarCliente = async () => {
  try {
    if (clienteEditando.value) {
      // Actualizar cliente existente
      const response = await axios.put(`http://localhost:8080/clientes/editar-Cliente/${clienteEditando.value.id}`, nuevoCliente.value);
      console.log('Cliente actualizado:', response.data);
    } else {
      // Agregar nuevo cliente
      const response = await axios.post('http://localhost:8080/clientes/insertarCliente', nuevoCliente.value);
      console.log('Cliente agregado:', response.data);
    }
    await cargarClientes(); // Recargar la lista
    limpiarFormulario();
  } catch (error) {
    console.error('Error al guardar cliente:', error);
  }
};

// Función para eliminar cliente con confirmación
const eliminarCliente = async (id) => {
  const confirmar = window.confirm('¿Estás seguro de que deseas eliminar este cliente?');
  if (confirmar) {
    try {
      await axios.delete(`http://localhost:8080/clientes/eliminar-Cliente/${id}`);
      console.log('Cliente eliminado:', id);
      await cargarClientes(); // Recargar la lista
    } catch (error) {
      console.error('Error al eliminar cliente:', error);
    }
  }
};

// Función para editar cliente
const editarCliente = (cliente) => {
  clienteEditando.value = cliente;
  nuevoCliente.value = { ...cliente };
};

// Función para limpiar el formulario
const limpiarFormulario = () => {
  clienteEditando.value = null;
  nuevoCliente.value = {
    nombre: '',
    apellidos: '',
    dni: '',
    telefono: '',
    email: ''
  };
};
</script>

<template>
  <div class="container">
    <div class="row">
      <!-- Formulario -->
      <div class="col-md-12">
        <div class="card shadow p-4 mb-4">
          <h2 class="text-center">{{ clienteEditando ? 'Editar Cliente' : 'Formulario de Clientes' }}</h2>
          <form @submit.prevent="agregarCliente">
            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" class="form-control" id="nombre" v-model="nuevoCliente.nombre" required>
              </div>
              <div class="col-md-6 mb-3">
                <label for="apellidos" class="form-label">Apellidos</label>
                <input type="text" class="form-control" id="apellidos" v-model="nuevoCliente.apellidos" required>
              </div>
              <div class="col-md-6 mb-3">
                <label for="dni" class="form-label">DNI</label>
                <input type="text" class="form-control" id="dni" v-model="nuevoCliente.dni" required>
              </div>
              <div class="col-md-6 mb-3">
                <label for="telefono" class="form-label">Teléfono</label>
                <input type="text" class="form-control" id="telefono" v-model="nuevoCliente.telefono" required>
              </div>
              <div class="col-md-6 mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" v-model="nuevoCliente.email" required>
              </div>
            </div>
            <button type="submit" class="btn btn-primary w-100">{{ clienteEditando ? 'Actualizar Cliente' : 'Agregar Cliente' }}</button>
            <button type="button" class="btn btn-secondary w-100 mt-2" @click="limpiarFormulario">Cancelar</button>
          </form>
        </div>
      </div>

      <!-- Tabla de clientes -->
      <div class="col-md-12">
        <div class="card shadow p-4 mb-4">
          <h2 class="text-center mb-3">Tabla de Clientes</h2>
          <table class="table table-striped">
            <thead>
              <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Apellidos</th>
                <th>DNI</th>
                <th>Teléfono</th>
                <th>Email</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="cliente in clientes" :key="cliente.id">
                <td>{{ cliente.id }}</td>
                <td>{{ cliente.nombre }}</td>
                <td>{{ cliente.apellidos }}</td>
                <td>{{ cliente.dni }}</td>
                <td>{{ cliente.telefono }}</td>
                <td>{{ cliente.email }}</td>
                <td>
                  <button class="btn btn-danger" @click="eliminarCliente(cliente.id)">
                    <i class="bi bi-trash2-fill"></i> Eliminar
                  </button>
                  <button class="btn btn-warning" @click="editarCliente(cliente)">
                    <i class="bi bi-pencil-fill"></i> Editar
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  max-width: 1200px;
}

.table th, .table td {
  text-align: center;
}

.card {
  margin-bottom: 20px;
}

button {
  margin-right: 10px;
}
</style>
