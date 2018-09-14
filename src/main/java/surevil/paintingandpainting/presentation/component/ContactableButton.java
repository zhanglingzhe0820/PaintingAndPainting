package surevil.paintingandpainting.presentation.component;

import com.jfoenix.controls.JFXButton;

public class ContactableButton extends JFXButton {
    private boolean isValueRecorded = false;
    private String value;


    public ContactableButton() {
        super();
        this.setOnMouseEntered(
                event -> {
                    if (!isValueRecorded) {
                        this.value = this.getText();
                        isValueRecorded = true;
                    }
                    this.setText(value);
                }
        );
        this.setOnMouseExited(event -> this.setText(""));
    }

}
