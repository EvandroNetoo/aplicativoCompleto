package aplicativoCompleto.windows;

import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Listagem {
    private final MainApp mainApp;
    TableView<Map<String, Object>> tabela;
    ObservableList<Map<String, Object>> dados;

    public Listagem(MainApp mainApp) {
        this.mainApp = mainApp;
        this.dados = FXCollections.observableArrayList(this.mainApp.controladora.listar());
    }

    private void handleRemover(Map<String, Object> item) {
        mainApp.controladora.remover((String) item.get("id"));
        dados.remove(item);
    }

    private void handleEditar(Map<String, Object> item) {
        mainApp.mostrarTelaFormulario(item);
    }

    private TableView<Map<String, Object>> criarTabela() {
        tabela = new TableView<>();

        mainApp.controladora.camposListados().forEach((label, campo) -> {
            TableColumn<Map<String, Object>, Object> coluna = new TableColumn<>(label);
            coluna.setCellValueFactory(new MapValueFactory(campo));
            coluna.setSortable(mainApp.controladora.camposOrdenaveis().contains(campo));
            tabela.getColumns().add(coluna);
        });

        TableColumn<Map<String, Object>, Void> colunaAcoes = new TableColumn<>("Ações");

        colunaAcoes.setCellFactory(coluna -> new CelulaAcoes(item -> handleEditar(item), item -> handleRemover(item)));

        tabela.getColumns().add(colunaAcoes);

        tabela.setItems(dados);

        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        return tabela;
    }

    private Button criarBotaoAdicionar() {
        Button btnAdicionar = new Button("Adicionar");
        btnAdicionar.setOnAction(event -> {
            mainApp.mostrarTelaFormulario();
        });
        return btnAdicionar;
    }

    private Label criarLabelTitulo() {
        Label titulo = new Label("LISTAGEM");
        titulo.setFont(new Font(BaseRoot.TAMANHO_TITUTLO));
        return titulo;
    }

    public Parent getRoot() {
        VBox container = new VBox(20);
        container.setAlignment(Pos.TOP_CENTER);
        container.getChildren().addAll(criarLabelTitulo(), criarTabela(), criarBotaoAdicionar());
        return BaseRoot.criarBaseRoot(container);
    }
}
