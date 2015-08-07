package de.saxsys.gui.view;

import de.saxsys.gui.controller.GlobalController;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class RootPane extends BorderPane {
    public RootPane(GlobalController globalController) {
        setPrefWidth(1600.0);
        setPrefHeight(1000.0);

        Pane contentPane = new ContentPane(widthProperty(), globalController); //create the content pane and hand down the global window width

        setCenter(contentPane);
    }
}
