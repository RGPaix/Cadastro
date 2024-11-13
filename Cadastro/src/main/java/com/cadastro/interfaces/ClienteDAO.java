package com.cadastro.interfaces;

import com.cadastro.config.ConnectionFactory;
import com.cadastro.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements InterfaceClienteDAO {
    @Override
    public Cliente create(Cliente cliente){
        String query = "INSERT INTO \"Cliente\" (\"Nome\", \"Email\", \"Telefone\") VALUES (?, ?, ?)";
            Cliente c = new Cliente();

        try (Connection con = ConnectionFactory.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getEmail());
            ps.setLong(3, cliente.getTelefone());
            ps.executeUpdate();

                c.setNome(cliente.getNome());
                c.setEmail(cliente.getEmail());
                c.setTelefone(cliente.getTelefone());

        } catch (SQLException e){
            throw new RuntimeException("Erro ao salvar cliente", e);
        }
        return c;
    }
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String query = "SELECT * FROM \"Cliente\"";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefone(rs.getLong("telefone"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar clientes", e);
        }
        return clientes;
    }
    public void excluirCliente(int id) {
        String query = "DELETE FROM \"Cliente\" WHERE id = ?";

        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir cliente", e);
        }
    }
}