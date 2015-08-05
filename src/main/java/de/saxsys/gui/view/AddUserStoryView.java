package de.saxsys.gui.view;

import de.saxsys.gui.controller.AddUserStoryController;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddUserStoryView extends Stage {
    private AddUserStoryController addUserStoryController;

    public AddUserStoryView(AddUserStoryController addUserStoryController) {
        this.addUserStoryController = addUserStoryController;

        initModality(Modality.APPLICATION_MODAL);
        setTitle("Add UserStory");

        setScene(createScene());

        show();
    }

    private Scene createScene() {
        GridPane mainView = new GridPane();

        Label titleLabel = new Label("Title");
        titleLabel.setId("adduserstory_title_label");
        mainView.add(titleLabel, 0, 0);
        TextField titleField = new TextField();
        titleField.setId("adduserstory_title_field");
        mainView.add(titleField, 1, 0);

        Label descriptionLabel = new Label("Description");
        descriptionLabel.setId("adduserstory_description_label");
        mainView.add(descriptionLabel, 0, 1);
        TextArea descriptionField = new TextArea();
        descriptionField.setId("adduserstory_description_field");
        mainView.add(descriptionField, 1, 1);

        Label priorityLabel = new Label("Priority");
        priorityLabel.setId("adduserstory_priority_label");
        mainView.add(priorityLabel, 0, 2);

        ToggleGroup priorityGroup = new ToggleGroup();
        RadioButton highRadio = new RadioButton("High");
        highRadio.setId("adduserstory_priority_high_radio");
        highRadio.setToggleGroup(priorityGroup);
        RadioButton middleRadio = new RadioButton("Middle");
        middleRadio.setId("adduserstory_priority_middle_radio");
        middleRadio.setSelected(true);
        middleRadio.setToggleGroup(priorityGroup);
        RadioButton lowRadio = new RadioButton("Low");
        lowRadio.setId("adduserstory_priority_low_radio");
        lowRadio.setToggleGroup(priorityGroup);
        RadioButton veryLowRadio = new RadioButton("Very Low");
        veryLowRadio.setId("adduserstory_priority_veryLow_radio");
        veryLowRadio.setToggleGroup(priorityGroup);
        VBox radioView = new VBox();
        radioView.setId("adduserstory_priority_view");
        radioView.getChildren().addAll(highRadio, middleRadio, lowRadio, veryLowRadio);
        mainView.add(radioView, 1, 2);

        Button addUserStoryButton = new Button("Add UserStory");
        addUserStoryButton.setId("adduserstory_button");
        addUserStoryButton.addEventHandler(ActionEvent.ACTION, addUserStoryController);
        mainView.add(addUserStoryButton, 0, 3);

        return new Scene(mainView);
    }
}
