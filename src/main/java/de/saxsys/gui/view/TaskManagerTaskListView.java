package de.saxsys.gui.view;

import de.saxsys.model.Task;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.util.List;

public class TaskManagerTaskListView extends ListView<HBox> {
    DoubleBinding listWidth;
    List<Task> tasks;

    public TaskManagerTaskListView(List<Task> tasks, DoubleBinding listWidth) {
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
            TaskManagerTaskView taskView = new TaskManagerTaskView(task);
            taskView.setId(task.getTitle() + "_view");
            getItems().add(taskView);
        }
    }
}
