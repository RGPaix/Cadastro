package com.cadastro;

import com.cadastro.config.ConnectionFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Principal extends Application {

    public static void main(String[] args) {
        // Testando a conexão com o banco de dados
        try {
            if (ConnectionFactory.getConnection() != null) {
                System.out.println("Conexão estabelecida com sucesso!");
            } else {
                System.out.println("Falha ao estabelecer conexão!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao tentar conectar: " + e.getMessage());
        }

        // Iniciando a aplicação JavaFX
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/cadastro/layout.fxml"));

        primaryStage.setTitle("Cadastro de Contatos");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }
}