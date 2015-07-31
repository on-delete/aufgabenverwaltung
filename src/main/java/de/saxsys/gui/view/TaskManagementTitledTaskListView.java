package de.saxsys.gui.view;

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

    public TaskManagementTitledTaskListView(UserStory story, List<Task> tasks, DoubleBinding listWidth) {
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
        TaskManagementUserStoryTitleView userStoryTitleView = new TaskManagementUserStoryTitleView(story);
        userStoryTitleView.setId(story.getTitle() + "_title_view");
        getChildren().add(userStoryTitleView);
    }

    private void setTasks() {
        TaskManagementTaskListView taskListView = new TaskManagementTaskListView(tasks, listWidth);
        taskListView.setId(story.getTitle() + "_" + RowTitles.ROW_TITLES.get(Status.values()[0]).toLowerCase() + "_view");

        getChildren().add(taskListView);
    }
}