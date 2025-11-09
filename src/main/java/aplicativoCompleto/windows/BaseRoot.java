package aplicativoCompleto.windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class BaseRoot {

    public static int TAMANHO_TITUTLO = 40;

    public static StackPane criarBaseRoot(Node node) {
        VBox container = new VBox(node);
        container.setPadding(new Insets(32, 16, 32, 16));
        container.setMaxWidth(1024);
        container.setAlignment(Pos.TOP_CENTER);

        StackPane wrapper = new StackPane(container);
        wrapper.setAlignment(Pos.TOP_CENTER);
        return wrapper;
    }

}
