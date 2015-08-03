package de.saxsys.gui.view;

import de.saxsys.gui.controller.ExpendableController;
import de.saxsys.gui.controller.UserStoryController;
import de.saxsys.model.Task;
import de.saxsys.model.UserStory;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class TaskManagementTaskListView extends ListView<VBox> {
    UserStory topUserStoryModel;

    UserStoryController userStoryController;
    ExpendableController expansionController;

    List<Task> tasks;

    DoubleBinding listWidth;

    public TaskManagementTaskListView(List<Task> tasks, DoubleBinding listWidth, UserStory topUserStoryModel, UserStoryController userStoryController, ExpendableController expansionController) {
        this.userStoryController = userStoryController;
        this.expansionController = expansionController;

        this.topUserStoryModel = topUserStoryModel;

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
            TaskManagementTaskView taskView = new TaskManagementTaskView(task, topUserStoryModel, userStoryController, expansionController);
            taskView.setId("story-" + topUserStoryModel.getId() + "task-" + task.getId() + "_view");
            getItems().add(taskView);
        }
    }
}
