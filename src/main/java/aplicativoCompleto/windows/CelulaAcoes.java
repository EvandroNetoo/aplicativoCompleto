package aplicativoCompleto.windows;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import java.util.Map;
import java.util.function.Consumer;

public class CelulaAcoes extends TableCell<Map<String, Object>, Void> {

    private final Button btnEditar = new Button("🖊");
    private final Button btnRemover = new Button("🗑️");
    private final HBox container = new HBox(5, btnEditar, btnRemover);

    private final Consumer<Map<String, Object>> onEditar;
    private final Consumer<Map<String, Object>> onRemover;

    public CelulaAcoes(Consumer<Map<String, Object>> onEditar, Consumer<Map<String, Object>> onRemover) {
        this.onEditar = onEditar;
        this.onRemover = onRemover;

        container.setAlignment(Pos.CENTER);

        btnEditar.setOnAction(e -> {
            Map<String, Object> item = getTableView().getItems().get(getIndex());
            if (this.onEditar != null)
                this.onEditar.accept(item);
        });

        btnRemover.setOnAction(e -> {
            Map<String, Object> item = getTableView().getItems().get(getIndex());
            if (this.onRemover != null)
                this.onRemover.accept(item);
        });
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        setGraphic(empty ? null : container);
    }
}
