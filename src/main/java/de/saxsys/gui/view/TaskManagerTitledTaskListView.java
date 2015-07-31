package de.saxsys.gui.view;

import de.saxsys.model.Status;
import de.saxsys.model.Task;
import de.saxsys.model.UserStory;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.VBox;

import java.util.List;

public class TaskManagerTitledTaskListView extends VBox {
    DoubleBinding listWidth;
    List<Task> tasks;
    UserStory story;

    public TaskManagerTitledTaskListView(UserStory story, List<Task> tasks, DoubleBinding listWidth) {
        this.listWidth = listWidth;
        this.setPrefWidth(listWidth.get());
        this.listWidth.addListener((e) -> {
            this.setPrefWidth(listWidth.get());
        });

        this.tasks = tasks;

        this.story = story;

        setUserstory();
        setTasks();
    }

    private void setUserstory() {
        TaskManagerUserStoryTitleView userStoryTitleView = new TaskManagerUserStoryTitleView(story);
        userStoryTitleView.setId(story.getTitle() + "_title_view");
        getChildren().add(userStoryTitleView);
    }

    private void setTasks() {
        TaskManagerTaskListView taskListView = new TaskManagerTaskListView(tasks, listWidth);
        taskListView.setId(story.getTitle() + "_" + RowTitles.ROW_TITLES.get(Status.values()[0]).toLowerCase() + "_view");

        getChildren().add(taskListView);
    }
}
