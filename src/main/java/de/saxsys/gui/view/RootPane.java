package de.saxsys.gui.view;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class RootPane extends BorderPane {
    public RootPane() {
        setPrefWidth(1500.0);

        Pane contentPane = new ContentPane(widthProperty()); //create the content pane and hand down the global window width
        contentPane.setPadding(new Insets(20.0, 50.0, 50.0, 50.0));
        contentPane.setId("content");

        setCenter(contentPane);
    }
}
