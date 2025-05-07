package com.fer.Clientes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    // Método GET para obtener todos los clientes
    @GetMapping("/traerClientes")
    public List<Cliente> traerClientes() {
        return clienteRepository.findAll();
    }

    // Método POST para insertar un nuevo cliente
    @PostMapping("/insertarCliente")
    public Cliente insertarCliente(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}
