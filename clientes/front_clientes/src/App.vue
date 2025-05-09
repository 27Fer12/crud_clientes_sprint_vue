<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const clientes = ref([])

const cargarClientes = async () => {
  const response = await axios.get('http://localhost:8080/clientes/traerClientes')
  clientes.value = response.data
  console.log(clientes.value)
}

onMounted(cargarClientes)
</script>

<template>
  <div class="container">
    <button class="btn btn-primary">Botón</button>
  </div>
</template>

<style scoped>
</style>
