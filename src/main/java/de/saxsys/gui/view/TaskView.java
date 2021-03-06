package de.saxsys.gui.view;

import de.saxsys.gui.controller.ExpendableController;
import de.saxsys.gui.controller.UserStoryController;
import de.saxsys.model.Task;
import de.saxsys.model.UserStory;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class TaskView extends VBox implements ActiveViewElement, Expandable {
    Task taskModel;
    UserStory topUserStoryModel;

    UserStoryController userStoryController;
    ExpendableController expansionController;

    public TaskView(Task taskModel, UserStory topUserStoryModel, UserStoryController userStoryController, ExpendableController expansionController) {
        this.taskModel = taskModel;
        this.taskModel.registerView(this);
        this.topUserStoryModel = topUserStoryModel;

        this.userStoryController = userStoryController;
        this.expansionController = expansionController;

        //build view
        setSimpleView();
    }

    private void setSimpleView() {
        setPadding(new Insets(5.0, 5.0, 5.0, 5.0));

        HBox simpleView = new HBox();
        simpleView.setSpacing(20.0);

        Hyperlink taskTitle = new Hyperlink("#T-" + taskModel.getId() + ": " + taskModel.getTitle());
        taskTitle.setPrefWidth(100.0);
        taskTitle.setId("story-" + topUserStoryModel.getId() + "task-" + taskModel.getTitle() + "_title_button");
        taskTitle.addEventHandler(ActionEvent.ACTION, expansionController);

        Button moveUpButton = new Button("Move Up");
        moveUpButton.setId("story-" + topUserStoryModel.getId() + "task-" + taskModel.getTitle() + "_moveup_button");
        moveUpButton.addEventHandler(ActionEvent.ACTION, userStoryController);

        Button moveDownButton = new Button("Move Down");
        moveDownButton.setId("story-" + topUserStoryModel.getId() + "task-" + taskModel.getTitle() + "_movedown_button");
        moveDownButton.addEventHandler(ActionEvent.ACTION, userStoryController);

        Button moveRightButton = new Button("Move Right");
        moveRightButton.setId("story-" + topUserStoryModel.getId() + "task-" + taskModel.getTitle() + "_moveright_button");
        moveRightButton.addEventHandler(ActionEvent.ACTION, userStoryController);

        Button deleteButton = new Button("Delete");
        deleteButton.setId("story-" + topUserStoryModel.getId() + "task-" + taskModel.getTitle() + "_delete_button");
        deleteButton.addEventHandler(ActionEvent.ACTION, userStoryController);

        simpleView.getChildren().addAll(taskTitle, moveDownButton, moveUpButton, deleteButton, moveRightButton);
        getChildren().add(simpleView);
    }

    @Override
    public void switchExpansion() {
        if (getChildren().size() < 2) {
            GridPane detailedView = new GridPane();
            detailedView.setHgap(30.0);
            detailedView.setVgap(10.0);
            detailedView.setPadding(new Insets(5.0, 0.0, 0.0, 10.0));

            Text priorityText = new Text("Priority");
            priorityText.setId("story-" + topUserStoryModel.getId() + "task-" + taskModel.getTitle() + "_priority_text");
            priorityText.setFont(Font.font("Sans Serif", FontWeight.BLACK, 12));
            Text priorityValue = new Text(PriorityNames.PRIORITY_NAMES.get(taskModel.getPriority()));
            priorityText.setId("story-" + topUserStoryModel.getId() + "task-" + taskModel.getTitle() + "_priority_value");
            detailedView.add(priorityText, 0, 0);
            detailedView.add(priorityValue, 1, 0);

            Text descriptionText = new Text("Description");
            descriptionText.setId("story-" + topUserStoryModel.getId() + "task-" + taskModel.getTitle() + "_description_text");
            descriptionText.setFont(Font.font("Sans Serif", FontWeight.BLACK, 12));
            Text descriptionValue = new Text(taskModel.getDescription());
            descriptionText.setId("story-" + topUserStoryModel.getId() + "task-" + taskModel.getTitle() + "_description_value");
            detailedView.add(descriptionText, 0, 1);
            detailedView.add(descriptionValue, 1, 1);

            Text inChargeText = new Text("Person in Charge");
            inChargeText.setId("story-" + topUserStoryModel.getId() + "task-" + taskModel.getTitle() + "_inCharge_text");
            inChargeText.setFont(Font.font("Sans Serif", FontWeight.BLACK, 12));
            Text inChargeValue = new Text(taskModel.getInCharge());
            inChargeText.setId("story-" + topUserStoryModel.getId() + "task-" + taskModel.getTitle() + "_inCharge_value");
            detailedView.add(inChargeText, 0, 2);
            detailedView.add(inChargeValue, 1, 2);

            Button editButton = new Button("Edit");
            editButton.setId("story-" + topUserStoryModel.getId() + "task-" + taskModel.getTitle() + "_edit_button");
            editButton.addEventHandler(ActionEvent.ACTION, userStoryController);
            detailedView.add(editButton, 0, 3);

            getChildren().add(detailedView);
        } else {
            refresh();
        }
    }

    @Override
    public void refresh() {
        getChildren().clear();
        setSimpleView();
    }
}
