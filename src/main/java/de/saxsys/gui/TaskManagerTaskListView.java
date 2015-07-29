package de.saxsys.gui;

import java.util.List;
import de.saxsys.model.Task;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class TaskManagerTaskListView extends ListView<HBox> {
    public TaskManagerTaskListView(List<Task> tasks) {
        for (Task task : tasks) {
            TaskManagerTaskView taskView = new TaskManagerTaskView(task);
            taskView.setId(task.getTitle() + "_view");
            getItems().add(taskView);
        }
    }
}
