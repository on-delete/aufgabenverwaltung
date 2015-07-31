package de.saxsys.gui.view;

import de.saxsys.model.Task;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class TaskManagementTaskListView extends ListView<VBox> {
    DoubleBinding listWidth;
    List<Task> tasks;

    public TaskManagementTaskListView(List<Task> tasks, DoubleBinding listWidth) {
        this.listWidth = listWidth;
        this.setPrefWidth(listWidth.get());
        this.listWidth.addListener((e) -> {
            this.setPrefWidth(listWidth.get());
        });

        this.tasks = tasks;

        setTasks();
    }

    private void setTasks() {
        for (Task task : tasks) {
            TaskManagementTaskView taskView = new TaskManagementTaskView(task);
            taskView.setId(task.getTitle() + "_view");
            getItems().add(taskView);
        }
    }
}
