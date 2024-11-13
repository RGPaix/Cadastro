package com.cadastro;

import com.cadastro.config.ConnectionFactory;
import com.cadastro.interfaces.ClienteDAO;
import com.cadastro.model.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class CadastroController {

    @FXML
    private TableView<Cliente> tableClientes;

    @FXML
    private TableColumn<Cliente, Integer> colunaId;

    @FXML
    private TableColumn<Cliente, String> colunaNome;

    @FXML
    private TableColumn<Cliente, String> colunaEmail;

    @FXML
    private TableColumn<Cliente, Long> colunaTelefone;

    @FXML
    private TextField NomeField;

    @FXML
    private TextField TelefoneField;

    @FXML
    private Button btnLimpar;

    @FXML
    private Button btnSair;

    @FXML
    private Button btnSalvar;

    @FXML
    private TextField emailField;

    @FXML
    private Label mensagemLabel;

    @FXML
    private ObservableList<Cliente> clientesList = FXCollections.observableArrayList();

    private Connection conn; // A conexão com o banco de dados

    // Inicializa colunas e configura a lista
    public void initialize() {
        // Inicializa a conexão com o banco de dados (ajuste o caminho conforme necessário)
        this.conn = ConnectionFactory.getConnection();

        // Configura as colunas da tabela
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        // Carrega os clientes na tabela
        carregarClientesNaLista();
    }

    // Método para carregar os clientes do banco de dados para a lista
    public void carregarClientesNaLista() {
        String sql = "SELECT * FROM \"Cliente\"";
        ObservableList<Cliente> clientes = FXCollections.observableArrayList();

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id"),  // Assume-se que a tabela 'Cliente' tenha a coluna 'id'
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getLong("telefone")
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Atualiza a TableView com a lista de clientes
        tableClientes.setItems(clientes);
        clientesList.setAll(clientes); // Atualiza a lista observável

        // Debug: Verifique se os dados estão sendo carregados
        System.out.println("Clientes carregados: " + clientes.size());
        for (Cliente cliente : clientes) {
            System.out.println(cliente.getNome());
        }
    }

    // Método do botão "Salvar"
    @FXML
    private void salvarContato(ActionEvent event) {
        if (NomeField.getText().isEmpty()) {
            mensagemLabel.setText("O campo Nome é obrigatório.");
        } else {
            Cliente cliente = new Cliente();
            cliente.setNome(NomeField.getText());
            cliente.setEmail(emailField.getText());
            cliente.setTelefone(Long.parseLong(TelefoneField.getText()));

            ClienteDAO clienteDAO = new ClienteDAO();
            clienteDAO.create(cliente);

            // Atualiza a lista de clientes após salvar
            carregarClientesNaLista(); // Recarrega a lista de clientes
            mensagemLabel.setText("Contato salvo com sucesso!");
        }
    }

    // Método do botão "Excluir"
    @FXML
    private void excluirCliente(ActionEvent event) {
        Cliente clienteSelecionado = tableClientes.getSelectionModel().getSelectedItem();
        if (clienteSelecionado != null) {
            ClienteDAO clienteDAO = new ClienteDAO();
            clienteDAO.excluirCliente(clienteSelecionado.getId());

            // Atualiza a lista de clientes após excluir
            carregarClientesNaLista(); // Recarrega a lista de clientes
            mensagemLabel.setText("Contato excluído com sucesso!");
        } else {
            mensagemLabel.setText("Selecione um cliente para excluir.");
        }
    }

    // Método do botão "Limpar"
    @FXML
    private void limparContato(ActionEvent event) {
        NomeField.clear();
        emailField.clear();
        TelefoneField.clear();
        mensagemLabel.setText("");
    }

    // Método do botão "Sair"
    @FXML
    private void fecharTela(ActionEvent event) {
        btnSair.getScene().getWindow().hide();
    }
}
