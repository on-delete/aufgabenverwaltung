package de.saxsys.gui.view;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ContentPane extends GridPane {

    public ContentPane(ReadOnlyDoubleProperty topWidth) {


        //write the title of the application
        Text title = new Text("Task Management");
        title.setId("title");
        GridPane.setHalignment(title, HPos.LEFT);


        Pane table = new TablePane(topWidth); //create the task table pane and hand down the global window width
        table.setId("table");

        add(title, 0, 0, 3, 1);
        add(table, 0, 2, 3, 1);

        setPadding(new Insets(20.0, 50.0, 50.0, 50.0));
        setId("content");
        setVgap(10);
    }
}
