package de.saxsys.gui.view;

import de.saxsys.gui.controller.EditUserStoryController;
import de.saxsys.model.UserStory;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditUserStoryView extends Stage {
    EditUserStoryController editUserStoryController;
    UserStory userStoryModel;

    public EditUserStoryView(EditUserStoryController editUserStoryController, UserStory userStoryModel) {
        this.editUserStoryController = editUserStoryController;
        this.userStoryModel = userStoryModel;

        initModality(Modality.APPLICATION_MODAL);
        setScene(this.createScene());
        setTitle("Edit UserStory");

        show();
    }

    public Scene createScene() {
        GridPane mainView = new GridPane();

        //Title
        Label titleLabel = new Label("Title");
        titleLabel.setId("edituserstory_title_label");
        mainView.add(titleLabel, 0, 0);
        TextField titleField = new TextField();
        titleField.setText(userStoryModel.getTitle());
        titleField.setId("edituserstory_title_field");
        mainView.add(titleField, 1, 0);

        //Description
        Label descriptionLabel = new Label("Description");
        descriptionLabel.setId("edituserstory_description_label");
        mainView.add(descriptionLabel, 0, 1);
        TextArea descriptionField = new TextArea();
        descriptionField.setText(userStoryModel.getDescription());
        descriptionField.setId("edituserstory_description_field");
        mainView.add(descriptionField, 1, 1);

        //Priority
        Label priorityLabel = new Label("Priority");
        priorityLabel.setId("edituserstory_priority_label");
        mainView.add(priorityLabel, 0, 2);

        ToggleGroup priorityGroup = new ToggleGroup();
        RadioButton highRadio = new RadioButton("High");
        highRadio.setId("edituserstory_priority_high_radio");
        highRadio.setToggleGroup(priorityGroup);
        RadioButton middleRadio = new RadioButton("Middle");
        middleRadio.setId("edituserstory_priority_middle_radio");
        middleRadio.setToggleGroup(priorityGroup);
        RadioButton lowRadio = new RadioButton("Low");
        lowRadio.setId("edituserstory_priority_low_radio");
        lowRadio.setToggleGroup(priorityGroup);
        RadioButton veryLowRadio = new RadioButton("Very Low");
        veryLowRadio.setId("edituserstory_priority_veryLow_radio");
        veryLowRadio.setToggleGroup(priorityGroup);

        switch (userStoryModel.getPriority()) {
            case HIGH: highRadio.setSelected(true); break;
            case MIDDLE: middleRadio.setSelected(true); break;
            case LOW: lowRadio.setSelected(true); break;
            case VERY_LOW: veryLowRadio.setSelected(true); break;
        }

        VBox radioView = new VBox();
        radioView.setId("edituserstory_priority_view");
        radioView.getChildren().addAll(highRadio, middleRadio, lowRadio, veryLowRadio);
        mainView.add(radioView, 1, 2);

        //Edit Button
        Button editUserStoryButton = new Button("Edit UserStory");
        editUserStoryButton.setId("edituserstory_button");
        editUserStoryButton.addEventHandler(ActionEvent.ACTION, editUserStoryController);
        mainView.add(editUserStoryButton, 0, 3);

        return new Scene(mainView);
    }
}
