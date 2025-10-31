package aplicativoCompleto.windows;

import aplicativoCompleto.control.ControladoraProduto;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Window extends Application {
    private static ControladoraProduto controladora;

    @Override
    public void start(Stage stage) {
        new AddProdutoRecord("", 1, 2);
        TableView<Produto> tabela = new TableView<>();

        // Colunas
        TableColumn<Produto, String> colunaNome = new TableColumn<>("Nome");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Produto, Integer> colunaPreco = new TableColumn<>("Preço");
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        // Adiciona colunas à tabela
        tabela.getColumns().addAll(colunaNome, colunaPreco);

        // Dados
        ObservableList<Produto> dados = FXCollections.observableArrayList(
                new Produto("Evandro", "Evandro", 25, 25),
                new Produto("Evandro", "Evandro", 30, 25),
                new Produto("Evandro", "Evandro", 22, 25));

        tabela.setItems(dados);

        VBox root = new VBox(tabela);

        Scene scene = new Scene(root, 640, 480);

        stage.setTitle("Tabela JavaFX sem FXML");
        stage.setScene(scene);
        stage.show();
    }

    public static void setControladora(ControladoraProduto controladora) {
        Window.controladora = controladora;
    }
}