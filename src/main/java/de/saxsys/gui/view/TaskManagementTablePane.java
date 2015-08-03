package de.saxsys.gui.view;

import de.saxsys.gui.controller.GlobalController;
import de.saxsys.model.UserStory;
import de.saxsys.model.UserStoryList;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class TaskManagementTablePane extends VBox implements ActiveViewElement {
    private GlobalController globalController;

    private UserStoryList globalModelInstance;

    private DoubleBinding columnWidth;

    public TaskManagementTablePane(ReadOnlyDoubleProperty topWidth) {

        //create global controller
        globalController = new GlobalController();
        globalModelInstance = globalController.getGlobalModelInstance();

        //register to global model instance
        globalModelInstance.registerView(this);

        columnWidth = topWidth.subtract(100.0).divide(RowTitles.ROW_TITLES.size()); //calculate the width of each column from the width of the global window (including space for margin)

        //building view
        setHeadings();
        setUserstories();
        setAddButton();
    }

    private void setHeadings() {
        TaskManagementHeadingView headings = new TaskManagementHeadingView(columnWidth); //create a heading view element and hand down the width of each column
        headings.setId("headings");

        getChildren().add(headings);
    }

    private void setUserstories() {
        for (UserStory story : globalModelInstance.getUserStories()) {
            TaskManagementUserStoryView view = new TaskManagementUserStoryView(story, columnWidth, globalController); //create a userstory view element and hand down the width of each column and the global model instance
            view.setId("story-" + story.getId() + "_view");

            getChildren().add(view);
        }
    }

    private void setAddButton() {
        Button addButton = new Button("Add UserStory");
        addButton.setId("add_userstory_button");
        getChildren().add(addButton);
    }

    @Override
    public void refresh() {
        getChildren().clear();
        setHeadings();
        setUserstories();
        setAddButton();
    }
}
