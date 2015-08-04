package de.saxsys.gui.view;

import de.saxsys.gui.controller.ExpendableController;
import de.saxsys.gui.controller.GlobalController;
import de.saxsys.gui.controller.UserStoryController;
import de.saxsys.model.UserStory;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TaskManagementUserStoryTitleView extends VBox implements Expandable {
    ExpendableController expansionController;
    GlobalController globalController;
    UserStoryController userStoryController;

    UserStory modelStory;

    public TaskManagementUserStoryTitleView(UserStory modelStory, ExpendableController expansionController, GlobalController globalController, UserStoryController userStoryController) {
        this.globalController = globalController;
        this.modelStory = modelStory;
        this.expansionController = expansionController;
        this.userStoryController = userStoryController;

        //build view
        setSimpleView();
    }

    private void setSimpleView() {
        HBox simpleView = new HBox();

        Hyperlink storyTitle = new Hyperlink("#U-" + modelStory.getId() + ": " + modelStory.getTitle());
        storyTitle.setId("story-" + modelStory.getId() + "_title_link");
        storyTitle.addEventHandler(ActionEvent.ACTION, expansionController);

        Button moveUpButton = new Button("Move Up");
        moveUpButton.setId("story-" + modelStory.getId() + "_moveup_button");
        moveUpButton.addEventHandler(ActionEvent.ACTION, globalController);

        Button moveDownButton = new Button("Move Down");
        moveDownButton.setId("story-" + modelStory.getId() + "_movedown_button");
        moveDownButton.addEventHandler(ActionEvent.ACTION, globalController);


        Button addTaskButton = new Button("Add Task");
        addTaskButton.setId("story-" + modelStory.getId() + "_addtask_button");
        addTaskButton.addEventHandler(ActionEvent.ACTION, userStoryController);

        Button deleteButton = new Button("Delete");
        deleteButton.setId("story-" + modelStory.getId() + "_delete_button");
        deleteButton.addEventHandler(ActionEvent.ACTION, globalController);


        simpleView.getChildren().addAll(storyTitle, moveDownButton, moveUpButton, deleteButton, addTaskButton);
        getChildren().add(simpleView);
    }

    @Override
    public void switchExpansion() {
        if (getChildren().size() < 2) {
            GridPane detailedView = new GridPane();

            Text priorityText = new Text("Priority");
            priorityText.setId("story-" + modelStory.getId() + "_priority_text");
            Text priorityValue = new Text(PriorityNames.PRIORITY_NAMES.get(modelStory.getPriority()));
            priorityText.setId("story-" + modelStory.getId() + "_priority_value");
            detailedView.add(priorityText, 0, 0);
            detailedView.add(priorityValue, 1, 0);

            Text descriptionText = new Text("Description");
            descriptionText.setId("story-" + modelStory.getId() + "_description_text");
            Text descriptionValue = new Text(modelStory.getDescription());
            descriptionText.setId("story-" + modelStory.getId() + "_description_value");
            detailedView.add(descriptionText, 0, 1);
            detailedView.add(descriptionValue, 1, 1);

            getChildren().add(detailedView);
        } else {
            getChildren().clear();
            setSimpleView();
        }
    }
}
