package aplicativoCompleto.windows;

import java.util.Map;

import aplicativoCompleto.control.Controladora;
import aplicativoCompleto.domain.Produto;
import aplicativoCompleto.domain.Veiculo;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    protected Controladora<Produto> controladora;

    private Stage stage;

    public MainApp() {
        this.controladora = new Controladora(Produto.class);
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        mostrarTelaListagem();

        stage.setTitle("Aplicativo Completo");
        stage.setResizable(true);
        stage.show();

        Platform.runLater(() -> stage.setMaximized(true));
    }

    protected void mostrarTelaListagem() {
        stage.setScene(new Scene(new Listagem(this).getRoot()));
    }

    protected void mostrarTelaFormulario() {
        stage.setScene(new Scene(new Formulario(this, null).getRoot()));
    }

    protected void mostrarTelaFormulario(Map<String, Object> item) {
        stage.setScene(new Scene(new Formulario(this, item).getRoot()));
    }
}
