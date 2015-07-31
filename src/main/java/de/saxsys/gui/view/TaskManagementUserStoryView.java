package de.saxsys.gui.view;

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
            if (status.ordinal() == 0) {
                TaskManagerTitledTaskListView titledTaskListView = new TaskManagerTitledTaskListView(modelStory, getTaskListByStatus(status), columnWith);
                titledTaskListView.setId(modelStory.getTitle() + "_" + RowTitles.ROW_TITLES.get(status).toLowerCase() + "titled_view");
                getChildren().add(titledTaskListView);
            } else {
                TaskManagementTaskListView taskListView = new TaskManagementTaskListView((getTaskListByStatus(status)), columnWith);
                taskListView.setId(modelStory.getTitle() + "_" + RowTitles.ROW_TITLES.get(status).toLowerCase() + "_view");
                getChildren().add(taskListView);
            }
        }
    }

    private List<Task> getTaskListByStatus(Status status) {
        List<Task> tasks = modelStory.getTasks().stream()
                .filter((element) -> ((element.getStatus().equals(status)) ? true : false))
                .collect(Collectors.toList());

        return tasks;
    }

    @Override
    public void refresh() {
        setTasksLists();
    }
}
