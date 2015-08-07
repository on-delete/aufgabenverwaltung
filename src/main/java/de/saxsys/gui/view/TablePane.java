package de.saxsys.gui.view;

import de.saxsys.gui.controller.GlobalController;
import de.saxsys.model.UserStory;
import de.saxsys.model.UserStoryList;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class TablePane extends VBox implements ActiveViewElement {
    private GlobalController globalController;

    private UserStoryList globalModelInstance;

    private DoubleBinding columnWidth;

    public TablePane(ReadOnlyDoubleProperty topWidth, GlobalController globalController) {

        //create global controller
        this. globalController = globalController;
        globalModelInstance = globalController.getGlobalModelInstance();

        //register to global model instance
        globalModelInstance.registerView(this);

        columnWidth = topWidth.subtract(100.0).divide(RowTitles.ROW_TITLES.size()); //calculate the width of each column from the width of the global window (including space for margin)

        //building view
        setSpacing(10.0);
        setHeadings();
        setUserstories();
        setAddButton();
    }

    private void setHeadings() {
        HeadingView headings = new HeadingView(columnWidth); //create a heading view element and hand down the width of each column
        headings.setId("headings");

        getChildren().add(headings);
    }

    private void setUserstories() {
        VBox userStories = new VBox();
        for (UserStory story : globalModelInstance.getUserStories()) {
            UserStoryView view = new UserStoryView(story, columnWidth, globalController); //create a userstory view element and hand down the width of each column and the global model instance
            view.setId("story-" + story.getId() + "_view");

            userStories.getChildren().add(view);
        }

        ScrollPane scrollPane = new ScrollPane(userStories);
        scrollPane.setPrefHeight(1000.00);
        getChildren().add(scrollPane);
    }

    private void setAddButton() {
        Button addButton = new Button("Add UserStory");
        addButton.setId("add_userstory_button");
        addButton.addEventHandler(ActionEvent.ACTION, globalController);
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
