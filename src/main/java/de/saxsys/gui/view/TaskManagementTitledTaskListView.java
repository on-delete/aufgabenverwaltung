package de.saxsys.gui.view;

import de.saxsys.gui.controller.ExpendableController;
import de.saxsys.model.Status;
import de.saxsys.model.Task;
import de.saxsys.model.UserStory;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.VBox;

import java.util.List;

public class TaskManagementTitledTaskListView extends VBox {
    DoubleBinding listWidth;
    List<Task> tasks;
    UserStory story;
    ExpendableController expansionController;

    public TaskManagementTitledTaskListView(UserStory story, List<Task> tasks, DoubleBinding listWidth, ExpendableController expansionController) {
       this.expansionController = expansionController;

        this.listWidth = listWidth;
        this.setPrefWidth(listWidth.get());
        this.listWidth.addListener((e) -> {
            this.setPrefWidth(listWidth.get());
        });

        this.tasks = tasks;

        this.story = story;

        //build view
        setUserstory();
        setTasks();
    }

    private void setUserstory() {
        TaskManagementUserStoryTitleView userStoryTitleView = new TaskManagementUserStoryTitleView(story, expansionController);
        userStoryTitleView.setId(story.getTitle() + "_title_view");
        getChildren().add(userStoryTitleView);
    }

    private void setTasks() {
        TaskManagementTaskListView taskListView = new TaskManagementTaskListView(tasks, listWidth, expansionController);
        taskListView.setId(story.getTitle() + "_" + RowTitles.ROW_TITLES.get(Status.values()[0]).toLowerCase() + "_view");

        getChildren().add(taskListView);
    }
}
