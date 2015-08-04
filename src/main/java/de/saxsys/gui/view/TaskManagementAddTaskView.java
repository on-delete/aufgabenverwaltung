package de.saxsys.gui.view;

import de.saxsys.gui.controller.AddTaskController;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TaskManagementAddTaskView extends Stage{
    private AddTaskController addTaskController;

    public TaskManagementAddTaskView(AddTaskController addTaskController) {
        this.addTaskController = addTaskController;

        initModality(Modality.APPLICATION_MODAL);
        setTitle("Add Task");

        setScene(createScene());

        show();
    }

    private Scene createScene() {
        GridPane mainView = new GridPane();

        Label titleLabel = new Label("Title");
        titleLabel.setId("addtask_title_label");
        mainView.add(titleLabel, 0, 0);
        TextField titleField = new TextField();
        titleField.setId("addtask_title_field");
        mainView.add(titleField, 1, 0);

        Label descriptionLabel = new Label("Title");
        descriptionLabel.setId("addtask_description_label");
        mainView.add(descriptionLabel, 0, 1);
        TextArea descriptionField = new TextArea();
        descriptionField.setId("addtask_description_field");
        mainView.add(descriptionField, 1, 1);

        Label priorityLabel = new Label("Priority");
        priorityLabel.setId("addtask_priority_label");
        mainView.add(priorityLabel, 0, 2);

        ToggleGroup priorityGroup = new ToggleGroup();
        RadioButton highRadio = new RadioButton("High");
        highRadio.setId("addtask_priority_high_radio");
        highRadio.setToggleGroup(priorityGroup);
        RadioButton mediumRadio = new RadioButton("Middle");
        mediumRadio.setId("addtask_priority_middle_radio");
        mediumRadio.setSelected(true);
        mediumRadio.setToggleGroup(priorityGroup);
        RadioButton lowRadio = new RadioButton("Low");
        lowRadio.setId("addtask_priority_low_radio");
        lowRadio.setToggleGroup(priorityGroup);
        RadioButton veryLowRadio = new RadioButton("Very Low");
        veryLowRadio.setId("addtask_priority_veryLow_radio");
        veryLowRadio.setToggleGroup(priorityGroup);
        VBox radioView = new VBox();
        radioView.setId("addtask_priority_view");
        radioView.getChildren().addAll(highRadio, mediumRadio, lowRadio, veryLowRadio);
        mainView.add(radioView, 1, 2);

        Button addTaskButton = new Button("Add Task");
        addTaskButton.setId("addtask_button");
        addTaskButton.addEventHandler(ActionEvent.ACTION, addTaskController);
        mainView.add(addTaskButton, 0, 3);

        return new Scene(mainView);
    }
}
