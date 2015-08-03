package de.saxsys.gui.view;

import de.saxsys.gui.controller.ExpendableController;
import de.saxsys.gui.controller.GlobalController;
import de.saxsys.gui.controller.UserStoryController;
import de.saxsys.model.Status;
import de.saxsys.model.Task;
import de.saxsys.model.UserStory;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.VBox;

import java.util.List;

public class TaskManagementTitledTaskListView extends VBox {
    ExpendableController expansionController;
    GlobalController globalController;
    UserStoryController userStoryController;

    UserStory modelStory;
    List<Task> tasks;

    DoubleBinding listWidth;

    public TaskManagementTitledTaskListView(UserStory modelStory, List<Task> tasks, DoubleBinding listWidth, UserStoryController userStoryController, ExpendableController expansionController, GlobalController globalController) {
        this.globalController = globalController;
        this.expansionController = expansionController;
        this.userStoryController = userStoryController;

        this.listWidth = listWidth;
        this.setPrefWidth(listWidth.get());
        this.listWidth.addListener((e) -> {
            this.setPrefWidth(listWidth.get());
        });

        this.tasks = tasks;

        this.modelStory = modelStory;

        //build view
        setUserstory();
        setTasks();
    }

    private void setUserstory() {
        TaskManagementUserStoryTitleView userStoryTitleView = new TaskManagementUserStoryTitleView(modelStory, expansionController, globalController);
        userStoryTitleView.setId("story-" + modelStory.getId() + "_title_view");
        getChildren().add(userStoryTitleView);
    }

    private void setTasks() {
        TaskManagementTaskListView taskListView = new TaskManagementTaskListView(tasks, listWidth, modelStory, userStoryController, expansionController);
        taskListView.setId("story-" + modelStory.getId() + "_row-" + RowTitles.ROW_TITLES.get(Status.values()[0]).toLowerCase() + "_view");

        getChildren().add(taskListView);
    }
}
