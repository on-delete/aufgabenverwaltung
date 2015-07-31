package de.saxsys.gui.view;

import de.saxsys.gui.controller.ExpendableController;
import de.saxsys.model.UserStory;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TaskManagementUserStoryTitleView extends VBox implements Expandable {
    UserStory story;
    ExpendableController expansionController;

    public TaskManagementUserStoryTitleView(UserStory story, ExpendableController expansionController) {
        this.story = story;
        this.expansionController = expansionController;

        //build view
        setSimpleView();
    }

    private void setSimpleView() {
        HBox simpleView = new HBox();

        Hyperlink storyTitle = new Hyperlink(story.getTitle());
        storyTitle.setId("userstory_" + story.getTitle() + "_title_button");
        storyTitle.addEventHandler(ActionEvent.ACTION, expansionController);

        Button moveUpButton = new Button("Move Up");
        moveUpButton.setId("userstory_" + story.getTitle() + "_moveup_button");

        Button moveDownButton = new Button("Move Down");
        moveDownButton.setId("userstory_" + story.getTitle() + "_movedown_button");


        Button moveRightButton = new Button("Add Task");
        moveRightButton.setId("userstory_" + story.getTitle() + "_addtask_button");


        Button deleteButton = new Button("Delete");
        deleteButton.setId("userstory_" + story.getTitle() + "_delete_button");


        simpleView.getChildren().addAll(storyTitle, moveDownButton, moveUpButton, deleteButton, moveRightButton);
        getChildren().add(simpleView);
    }

    @Override
    public void switchExpansion() {
        if (getChildren().size() < 2) {
            GridPane detailedView = new GridPane();

            Text priorityText = new Text("Priority");
            priorityText.setId("userstorz_" + story.getTitle() + "_priority_text");
            Text priorityValue = new Text(PriorityNames.PRIORITY_NAMES.get(story.getPriority()));
            priorityText.setId("userstorz_" + story.getTitle() + "_priority_value");
            detailedView.add(priorityText, 0, 0);
            detailedView.add(priorityValue, 1, 0);

            Text descriptionText = new Text("Description");
            descriptionText.setId("userstorz_" + story.getTitle() + "_description_text");
            Text descriptionValue = new Text(story.getDescription());
            descriptionText.setId("userstorz_" + story.getTitle() + "_description_value");
            detailedView.add(descriptionText, 0, 1);
            detailedView.add(descriptionValue, 1, 1);

            getChildren().add(detailedView);
        } else {
            getChildren().clear();
            setSimpleView();
        }
    }
}
