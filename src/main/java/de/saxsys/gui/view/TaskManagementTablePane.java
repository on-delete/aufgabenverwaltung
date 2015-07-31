package de.saxsys.gui.view;

import de.saxsys.gui.controller.GlobalController;
import de.saxsys.gui.controller.GlobalMockController;
import de.saxsys.model.UserStory;
import de.saxsys.model.UserStoryList;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class TaskManagementTablePane extends VBox implements ActiveViewElement {
    private UserStoryList globalModelInstance;

    private DoubleBinding columnWidth;

    public TaskManagementTablePane(ReadOnlyDoubleProperty topWidth) {

        GlobalController controller = new GlobalMockController();
        globalModelInstance = controller.getGlobalModelInstance();
        globalModelInstance.registerView(this);

        columnWidth = topWidth.subtract(100.0).divide(RowTitles.ROW_TITLES.size()); //calculate the width of each column from the width of the top panes (including space for margin)

        setHeadings();
        setUserstories();
        setAddButton();
    }

    private void setHeadings() {
        TaskManagementHeadingView headings = new TaskManagementHeadingView(columnWidth);
        headings.setId("headings");

        getChildren().add(headings);
    }

    private void setUserstories() {
        for (UserStory story : globalModelInstance.getUserStories()) {
            TaskManagementUserStoryView view = new TaskManagementUserStoryView(story, columnWidth);
            view.setId(story.getTitle() + "_story");

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
        setUserstories();
    }
}
