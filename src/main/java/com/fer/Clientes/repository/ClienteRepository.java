package com.fer.Clientes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fer.Clientes.model.Cliente;

public interface ClienteRepository  extends JpaRepository<Cliente, Long> {


}
