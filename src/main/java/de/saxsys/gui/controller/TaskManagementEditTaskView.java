package de.saxsys.gui.controller;

import de.saxsys.model.Task;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TaskManagementEditTaskView extends Stage{
    EditTaskController editTaskController;
    Task taskModel;

    public TaskManagementEditTaskView(EditTaskController editTaskController, Task taskModel) {
        this.editTaskController = editTaskController;
        this.taskModel = taskModel;

        initModality(Modality.APPLICATION_MODAL);
        setScene(this.createScene());
        setTitle("Edit Task");

        show();
    }

    public Scene createScene() {
        GridPane mainView = new GridPane();

        //Title
        Label titleLabel = new Label("Title");
        titleLabel.setId("edittask_title_label");
        mainView.add(titleLabel, 0, 0);
        TextField titleField = new TextField();
        titleField.setText(taskModel.getTitle());
        titleField.setId("edittask_title_field");
        mainView.add(titleField, 1, 0);

        //Description
        Label descriptionLabel = new Label("Description");
        descriptionLabel.setId("edittask_description_label");
        mainView.add(descriptionLabel, 0, 1);
        TextArea descriptionField = new TextArea();
        descriptionField.setText(taskModel.getDescription());
        descriptionField.setId("edittask_description_field");
        mainView.add(descriptionField, 1, 1);

        //Priority
        Label priorityLabel = new Label("Priority");
        priorityLabel.setId("edittask_priority_label");
        mainView.add(priorityLabel, 0, 2);

        ToggleGroup priorityGroup = new ToggleGroup();
        RadioButton highRadio = new RadioButton("High");
        highRadio.setId("edittask_priority_high_radio");
        highRadio.setToggleGroup(priorityGroup);
        RadioButton middleRadio = new RadioButton("Middle");
        middleRadio.setId("edittask_priority_middle_radio");
        middleRadio.setToggleGroup(priorityGroup);
        RadioButton lowRadio = new RadioButton("Low");
        lowRadio.setId("edittask_priority_low_radio");
        lowRadio.setToggleGroup(priorityGroup);
        RadioButton veryLowRadio = new RadioButton("Very Low");
        veryLowRadio.setId("edittask_priority_veryLow_radio");
        veryLowRadio.setToggleGroup(priorityGroup);

        switch (taskModel.getPriority()) {
            case HIGH: highRadio.setSelected(true); break;
            case MIDDLE: middleRadio.setSelected(true); break;
            case LOW: lowRadio.setSelected(true); break;
            case VERY_LOW: veryLowRadio.setSelected(true); break;
        }

        VBox radioView = new VBox();
        radioView.setId("edittask_priority_view");
        radioView.getChildren().addAll(highRadio, middleRadio, lowRadio, veryLowRadio);
        mainView.add(radioView, 1, 2);

        //inCharge
        Label inChargeLabel = new Label("Person in Charge");
        inChargeLabel.setId("edittask_inCharge_label");
        mainView.add(inChargeLabel, 0, 3);
        TextField inChargeField = new TextField();
        inChargeField.setText(taskModel.getInCharge());
        inChargeField.setId("edittask_inCharge_field");
        mainView.add(inChargeField, 1, 3);

        //Edit Button
        Button editTaskButton = new Button("Edit Task");
        editTaskButton.setId("edittask_button");
        editTaskButton.addEventHandler(ActionEvent.ACTION, editTaskController);
        mainView.add(editTaskButton, 0, 4);

        return new Scene(mainView);
    }
}
