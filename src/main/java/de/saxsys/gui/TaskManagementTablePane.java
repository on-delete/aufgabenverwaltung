package de.saxsys.gui;

import de.saxsys.model.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.HPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public void refresh() {
        setUserstories();
    }
}
