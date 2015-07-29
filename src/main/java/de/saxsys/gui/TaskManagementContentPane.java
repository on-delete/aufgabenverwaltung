package de.saxsys.gui;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.geometry.HPos;
import javafx.scene.layout.Pane;

public class TaskManagementContentPane extends GridPane {
    public TaskManagementContentPane() {

        Text title = new Text("Task Management");
        title.setId("title");
        GridPane.setHalignment(title, HPos.LEFT);


        Pane table = new TaskManagementTablePane();
        table.setId("table");

        add(title, 0, 0, 3, 1);
        add(table, 0, 2, 3, 1);
        
        setVgap(10);
    }
}
