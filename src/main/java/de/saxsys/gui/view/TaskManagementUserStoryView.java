package de.saxsys.gui.view;

import de.saxsys.gui.controller.ExpendableController;
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
    ExpendableController expansionController;

    public TaskManagementUserStoryView(UserStory modelStory, DoubleBinding columnWith, ExpendableController expansionController) {
       this.expansionController = expansionController;

        this.modelStory = modelStory;
        //register to assigned UserStory model
        modelStory.registerView(this);

        this.columnWith = columnWith;

        //build view
        setTasksLists();
    }

    public void setTasksLists() {
        for (Status status : Status.values()) {
            if (status.ordinal() == 0) {
                //adds a view element with userstory title and tasks of the column if its the first column
                TaskManagementTitledTaskListView titledTaskListView = new TaskManagementTitledTaskListView(modelStory, getTaskListByStatus(status), columnWith, expansionController);
                titledTaskListView.setId(modelStory.getTitle() + "_" + RowTitles.ROW_TITLES.get(status).toLowerCase() + "titled_view");
                getChildren().add(titledTaskListView);
            } else {
                //otherwise add an view element which only contains the tasks of the column
                TaskManagementTaskListView taskListView = new TaskManagementTaskListView((getTaskListByStatus(status)), columnWith, expansionController);
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
        getChildren().clear();
        setTasksLists();
    }
}
