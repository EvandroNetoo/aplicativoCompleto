package aplicativoCompleto.windows;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import aplicativoCompleto.control.ValidacaoException;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class Formulario {
    private MainApp mainApp;
    private List<TextField> inputs;
    private Map<String, Object> item;
    private Map<String, Label> errosLabels;

    public Formulario(MainApp mainApp, Map<String, Object> item) {
        this.mainApp = mainApp;
        this.inputs = new LinkedList<>();
        this.item = item;
        this.errosLabels = new HashMap<>();
    }

    public VBox criarFormulario() {
        VBox formulario = new VBox(10);
        mainApp.controladora.camposAdicionais().forEach((label, campo) -> {
            VBox containerInput = new VBox(5);
            Label lbl = new Label(label);
            TextField input = new TextField();
            input.getProperties().put("name", campo);
            input.setPromptText(label);
            Label lblErro = new Label();
            lblErro.setStyle("-fx-text-fill: red;");
            if (item != null && item.containsKey(campo)) {
                input.setText(item.get(campo).toString());
            }
            containerInput.getChildren().addAll(lbl, input, lblErro);
            formulario.getChildren().add(containerInput);
            this.inputs.add(input);
            this.errosLabels.put(campo, lblErro);
        });

        return formulario;
    }

    protected Button criarBotaoEnviar() {
        Button btnEnviar = new Button("Enviar");
        btnEnviar.setOnAction(event -> {
            Map<String, String> dadosItem = new java.util.HashMap<>();
            for (TextField input : inputs) {
                String nomeCampo = (String) input.getProperties().get("name");
                String valorCampo = input.getText();
                dadosItem.put(nomeCampo, valorCampo);
            }
            try {
                if (item != null && item.containsKey("id")) {
                    String id = (String) item.get("id");
                    mainApp.controladora.atualizar(id, dadosItem);
                } else {
                    mainApp.controladora.adicionar(dadosItem);
                }
                mainApp.mostrarTelaListagem();
            } catch (ValidacaoException e) {
                for (String campo : e.getErros().keySet()) {
                    List<String> mensagensErro = e.getErros().get(campo);
                    Label lblErro = this.errosLabels.get(campo);
                    lblErro.setText("");
                    lblErro.setText(String.join("\n", mensagensErro));

                }
            }
        });
        return btnEnviar;
    }

    private Button criarBtnVoltar() {
        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(event -> {
            mainApp.mostrarTelaListagem();
        });
        return btnVoltar;
    }

    private HBox criarContainerBotoes() {
        HBox btnsContainer = new HBox();

        Region espaco = new Region();
        HBox.setHgrow(espaco, Priority.ALWAYS);

        btnsContainer.getChildren().addAll(criarBotaoEnviar(), espaco, criarBtnVoltar());
        return btnsContainer;
    }

    private Label criarLabelTitulo() {
        Label titulo = new Label(item == null ? "ADICIONAR" : "EDITAR");
        titulo.setFont(new Font(BaseRoot.TAMANHO_TITUTLO));
        return titulo;
    }

    protected Parent getRoot() {
        VBox container = new VBox(20);
        container.setAlignment(Pos.CENTER);
        container.setMaxWidth(400);

        container.getChildren().addAll(criarLabelTitulo(), criarFormulario(), criarContainerBotoes());

        return BaseRoot.criarBaseRoot(container);
    }

}
