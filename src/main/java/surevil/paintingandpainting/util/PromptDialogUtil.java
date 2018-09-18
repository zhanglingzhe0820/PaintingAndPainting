package surevil.paintingandpainting.util;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class PromptDialogUtil {
    public static void show(StackPane container, String title, String text) {
        JFXDialogLayout layout = new JFXDialogLayout();
        JFXButton button = new JFXButton("好", new MaterialIconView(MaterialIcon.CHECK));
        layout.setBody(new Label(text));
        layout.setHeading(new Label(title));
        layout.setActions(button);
        JFXDialog dialog = new JFXDialog(container, layout, JFXDialog.DialogTransition.CENTER);
        button.setOnAction(e -> dialog.close());
        dialog.show();
    }
}
