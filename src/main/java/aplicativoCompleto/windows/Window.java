package aplicativoCompleto.windows;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import aplicativoCompleto.control.ControladoraProduto;
import javafx.application.Application;
import javafx.application.Platform;
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
    TableView<Map<String, Object>> tabela;
    ObservableList<Map<String, Object>> dados;

    public Window() {
        this.controladora = new ControladoraProduto();
        this.dados = FXCollections.observableArrayList(controladora.listar());
    }

    private void handleRemover(Map<String, Object> item) {
        controladora.remover((String) item.get("id"));
        dados.remove(item);
    }

    private void handleEditar(Map<String, Object> item) {
        System.out.println(item);
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
            Map<String, String> dadosItem = new java.util.HashMap<>();
            for (TextField input : inputs) {
                String nomeCampo = (String) input.getProperties().get("name");
                String valorCampo = input.getText();
                dadosItem.put(nomeCampo, valorCampo);
            }
            dados.add(controladora.adicionar(dadosItem));
            tabela.sort();
        });

        formulario.getChildren().add(btnEnviar);

        return formulario;
    }

    private VBox criarTabela() {
        VBox containerTabela = new VBox(10);
        tabela = new TableView<>();

        controladora.camposListados().forEach((label, campo) -> {
            TableColumn<Map<String, Object>, Object> coluna = new TableColumn<>(label);
            coluna.setCellValueFactory(new MapValueFactory(campo));
            coluna.setSortable(controladora.camposOrdenaveis().contains(campo));
            tabela.getColumns().add(coluna);
        });

        TableColumn<Map<String, Object>, Void> colunaAcoes = new TableColumn<>("Ações");

        colunaAcoes.setCellFactory(coluna -> new CelulaAcoes(item -> handleEditar(item), item -> handleRemover(item)));

        tabela.getColumns().add(colunaAcoes);

        tabela.setItems(dados);
        // tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        containerTabela.getChildren().add(tabela);
        return containerTabela;
    }

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(20, criarTabela(), criarFormulario());
        root.setPadding(new Insets(40));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Aplicativo Completo");
        stage.setResizable(true);
        stage.show();

        Platform.runLater(() -> stage.setMaximized(true));
    }
}
