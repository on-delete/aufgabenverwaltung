package de.saxsys.gui.view;

import de.saxsys.gui.controller.ExpendableController;
import de.saxsys.gui.controller.GlobalController;
import de.saxsys.gui.controller.UserStoryController;
import de.saxsys.model.Status;
import de.saxsys.model.Task;
import de.saxsys.model.UserStory;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.stream.Collectors;

public class UserStoryView extends HBox implements ActiveViewElement {
    UserStory modelStory;

    GlobalController globalController;
    UserStoryController userStoryController;
    ExpendableController expendableController;

    DoubleBinding columnWith;


    public UserStoryView(UserStory modelStory, DoubleBinding columnWith, GlobalController globalController) {
        this.globalController = globalController;
        this.userStoryController = new UserStoryController(modelStory);
        //create an expandable controller for all sub-elements
        this.expendableController = new ExpendableController();

        this.modelStory = modelStory;

        //register to assigned UserStory model
        this.modelStory.registerView(this);

        this.columnWith = columnWith;

        //build view
        setTasksLists();
    }

    public void setTasksLists() {
        for (Status status : Status.values()) {
            if (status.ordinal() == 0) {
                //adds a view element with userstory title and tasks of the column if its the first column
                TitledTaskListView titledTaskListView = new TitledTaskListView(modelStory, getTaskListByStatus(status), columnWith, userStoryController, expendableController, globalController);
                titledTaskListView.setId("story-" + modelStory.getId() + "_row-" + RowTitles.ROW_TITLES.get(status).toLowerCase() + "_titled_view");
                getChildren().add(titledTaskListView);
            } else {
                //otherwise add an view element which only contains the tasks of the column
                TaskListView taskListView = new TaskListView((getTaskListByStatus(status)), columnWith, modelStory, userStoryController, expendableController);
                taskListView.setId("story-" + modelStory.getId() + "_row-" + RowTitles.ROW_TITLES.get(status).toLowerCase() + "_view");
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
