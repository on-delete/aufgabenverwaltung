package de.saxsys.gui;

import de.saxsys.model.Task;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class TaskManagerTaskView extends HBox {
    public TaskManagerTaskView(Task task) {
       Text taskTitle = new Text(task.getTitle());
       Button moveUpButton = new Button("Move Up");
       Button moveDownButton = new Button("Move Down");
       Button moveRightButton = new Button("Move Right");
       Button deleteButton = new Button("Delete");
       
       getChildren().addAll(taskTitle, moveDownButton, moveUpButton, deleteButton, moveRightButton);
    }
}
