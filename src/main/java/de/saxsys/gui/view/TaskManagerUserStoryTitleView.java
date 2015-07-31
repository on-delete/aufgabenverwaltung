package de.saxsys.gui.view;

import de.saxsys.model.UserStory;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class TaskManagerUserStoryTitleView extends HBox{
    public TaskManagerUserStoryTitleView(UserStory story) {
        Text taskTitle = new Text(story.getTitle());
        Button moveUpButton = new Button("Move Up");
        Button moveDownButton = new Button("Move Down");
        Button moveRightButton = new Button("AddTask");
        Button deleteButton = new Button("Delete");

        getChildren().addAll(taskTitle, moveDownButton, moveUpButton, deleteButton, moveRightButton);
    }
}
