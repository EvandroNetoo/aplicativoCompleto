package aplicativoCompleto.windows;

import java.util.Map;

import aplicativoCompleto.control.ControladoraProduto;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Window extends Application {
    private ControladoraProduto controladora;

    public Window() {
        this.controladora = new ControladoraProduto();
    }

    @Override
    public void start(Stage stage) {
        TableView<Map<String, Object>> tabela = new TableView<>();

        controladora.camposListados().forEach((label, campo) -> {
            TableColumn<Map<String, Object>, Object> coluna = new TableColumn<>(label);
            coluna.setCellValueFactory(new MapValueFactory(campo));
            tabela.getColumns().add(coluna);
        });

        // Dados
        ObservableList<Map<String, Object>> dados = FXCollections.observableList(controladora.listarProdutos());

        tabela.setItems(dados);

        VBox root = new VBox(tabela);
        root.setPadding(new Insets(40));

        Scene scene = new Scene(root, 640, 480);

        stage.setTitle("Tabela JavaFX sem FXML");
        stage.setScene(scene);
        stage.show();
    }
}