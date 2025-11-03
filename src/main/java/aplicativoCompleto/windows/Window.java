package aplicativoCompleto.windows;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import aplicativoCompleto.control.ControladoraProduto;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Window extends Application {
    private ControladoraProduto controladora;

    public Window() {
        this.controladora = new ControladoraProduto();
    }

    private VBox criarFormulario() {
        VBox formulario = new VBox(10);
        List<TextField> inputs = new LinkedList<TextField>();

        controladora.camposAdicionais().forEach((label, campo) -> {
            VBox containerInput = new VBox(5);
            Label lbl = new Label(label);
            TextField input = new TextField();
            input.getProperties().put("name", campo);
            inputs.add(input);
            input.setPromptText(label);
            containerInput.getChildren().addAll(lbl, input);
            formulario.getChildren().add(containerInput);
        });
        Button btnEnviar = new Button("Enviar");
        btnEnviar.setOnAction(event -> {
            Map<String, String> dadosProduto = new java.util.HashMap<>();
            for (TextField input : inputs) {
                String nomeCampo = (String) input.getProperties().get("name");
                String valorCampo = input.getText();
                dadosProduto.put(nomeCampo, valorCampo);
            }
            controladora.adicionar(dadosProduto);
        });
        formulario.getChildren().add(btnEnviar);
        return formulario;
    }

    private VBox criarTabela() {
        VBox containerTabela = new VBox(10);
        TableView<Map<String, Object>> tabela = new TableView<>();

        controladora.camposListados().forEach((label, campo) -> {
            TableColumn<Map<String, Object>, Object> coluna = new TableColumn<>(label);
            coluna.setCellValueFactory(new MapValueFactory(campo));
            tabela.getColumns().add(coluna);
        });

        // Dados
        ObservableList<Map<String, Object>> dados = FXCollections.observableList(controladora.listar());

        tabela.setItems(dados);
        containerTabela.getChildren().add(tabela);
        return containerTabela;
    }

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(20, criarTabela(), criarFormulario());
        root.setPadding(new Insets(40));

        Scene scene = new Scene(root, 640, 480);

        stage.setTitle("Aplicativo Completo");
        stage.setScene(scene);
        stage.show();
    }
}
