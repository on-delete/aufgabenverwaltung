package de.saxsys.gui;

import de.saxsys.model.Status;
import de.saxsys.model.Task;
import de.saxsys.model.UserStory;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.stream.Collectors;

public class TaskManagementUserStoryView extends HBox implements ActiveViewElement {

    DoubleBinding columnWith;
    UserStory modelStory;

    public TaskManagementUserStoryView(UserStory modelStory, DoubleBinding columnWith) {
        this.modelStory = modelStory;
        modelStory.registerView(this);

        this.columnWith = columnWith;

        setTasksLists();
    }

    public void setTasksLists() {
        getChildren().clear();

        for (Status status : Status.values()) {
            TaskManagerTaskListView taskListView = getTaskListViewByStatus(status);
            taskListView.setId(modelStory.getTitle() + "_" + RowTitles.ROW_TITLES.get(status).toLowerCase() + "_view");

            getChildren().add(taskListView);
        }
    }

    private TaskManagerTaskListView getTaskListViewByStatus(Status status) {
        List<Task> tasks = modelStory.stream()
                .filter((element) -> ((element.getStatus().equals(status)) ? true : false))
                .collect(Collectors.toList());

        return new TaskManagerTaskListView(tasks, columnWith);
    }

    @Override
    public void refresh() {
        setTasksLists();
    }
}
