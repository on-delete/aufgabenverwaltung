package de.saxsys.gui;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.geometry.HPos;
import javafx.scene.layout.Pane;

public class TaskManagementContentPane extends GridPane {
    ReadOnlyDoubleProperty topWidth;
    public TaskManagementContentPane(ReadOnlyDoubleProperty topWidth) {

        this.topWidth = topWidth;

        Text title = new Text("Task Management");
        title.setId("title");
        GridPane.setHalignment(title, HPos.LEFT);


        Pane table = new TaskManagementTablePane(topWidth);
        table.setId("table");

        add(title, 0, 0, 3, 1);
        add(table, 0, 2, 3, 1);
        
        setVgap(10);
    }
}
