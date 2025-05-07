package com.fer.Clientes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fer.Clientes.model.Cliente;
import com.fer.Clientes.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // Obtener todos los clientes
    @GetMapping("/traerClientes")
    public List<Cliente> traerClientes() {
        return clienteRepository.findAll();
    }

    // Insertar un nuevo cliente
    @PostMapping("/insertarCliente")
    public Cliente insertarCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Editar un cliente existente
    @PutMapping("/editar-Cliente/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        return clienteRepository.findById(id).map(clienteExistente -> {
            clienteExistente.setNombre(cliente.getNombre());
            clienteExistente.setApellidos(cliente.getApellidos());
            clienteExistente.setDni(cliente.getDni());
            clienteExistente.setTelefono(cliente.getTelefono());
            clienteExistente.setEmail(cliente.getEmail());
            Cliente clienteActualizado = clienteRepository.save(clienteExistente);
            return ResponseEntity.ok(clienteActualizado);
        }).orElse(ResponseEntity.notFound().build());
    }
}
