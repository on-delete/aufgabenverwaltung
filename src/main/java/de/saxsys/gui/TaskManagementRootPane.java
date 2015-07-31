package de.saxsys.gui;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class TaskManagementRootPane extends BorderPane {
    public TaskManagementRootPane() {
        setPrefWidth(1500.0);
       Pane contentPane = new TaskManagementContentPane(widthProperty());
       contentPane.setPadding(new Insets(20.0, 50.0, 50.0, 50.0));
       contentPane.setId("content");
       setCenter(contentPane);
    }
}
