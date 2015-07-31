package de.saxsys.gui.view;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class TaskManagementContentPane extends GridPane {

    public TaskManagementContentPane(ReadOnlyDoubleProperty topWidth) {

        //write the title of the application
        Text title = new Text("Task Management");
        title.setId("title");
        GridPane.setHalignment(title, HPos.LEFT);


        Pane table = new TaskManagementTablePane(topWidth); //create the task table pane and hand down the global window width
        table.setId("table");

        add(title, 0, 0, 3, 1);
        add(table, 0, 2, 3, 1);
        
        setVgap(10);
    }
}
