package com.cadastro.interfaces;

import com.cadastro.model.Cliente;

import java.util.Optional;

public interface InterfaceClienteDAO {
    Cliente create(Cliente cliente);
}