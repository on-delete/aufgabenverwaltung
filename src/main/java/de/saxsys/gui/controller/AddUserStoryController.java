package de.saxsys.gui.controller;

import de.saxsys.model.Priority;
import de.saxsys.model.UserStory;
import de.saxsys.model.UserStoryList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class AddUserStoryController implements EventHandler<ActionEvent>{
    UserStoryList globalModel;

    public AddUserStoryController(UserStoryList globalModel) {
        this.globalModel = globalModel;
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getEventType().equals(ActionEvent.ACTION) && event.getSource() instanceof Button) {
            Button source = (Button) event.getSource();
            if (source.getParent() instanceof GridPane) {
                GridPane mainView = (GridPane) source.getParent();

                UserStory inputUserStory = generateUserStoryFromInput(mainView);

                globalModel.addUserStory(inputUserStory);
            }
        }
    }

    private UserStory generateUserStoryFromInput(GridPane mainView) {
        String title = getTitleFromInput(mainView);
        Priority priority = getPriorityFromInput(mainView);
        String description = getDescriptionFromInput(mainView);

        UserStory generatedUserStory = new UserStory(-1, title, priority, description);

        int id = sendToService(generatedUserStory);

        generatedUserStory.setId(id);

        return generatedUserStory;
    }

    private String getTitleFromInput(GridPane mainView) {
        if (mainView.lookup("#adduserstory_title_field") instanceof TextField) {
            TextField titleField = (TextField) mainView.lookup("#adduserstory_title_field");
            return titleField.getText();
        } else {
            throw new IllegalArgumentException("Incorrect parent pane");
        }
    }

    private String getDescriptionFromInput(GridPane mainView) {
        if (mainView.lookup("#adduserstory_description_field") instanceof TextArea) {
            TextArea descriptionField = (TextArea) mainView.lookup("#adduserstory_description_field");
            return descriptionField.getText();
        } else {
            throw new IllegalArgumentException("Incorrect parent pane");
        }
    }

    private Priority getPriorityFromInput(GridPane mainView) {
        if (mainView.lookup("#adduserstory_priority_view") instanceof VBox) {
            VBox priorityRadioView = (VBox) mainView.lookup("#adduserstory_priority_view");

            Optional<Priority> chosen = priorityRadioView.getChildren().stream()
                    .filter((element) -> {
                        if (element instanceof RadioButton) {
                            RadioButton radioElement = (RadioButton) element;
                            return radioElement.isSelected();
                        } else {
                            return false;
                        }
                    })
                    .map((element) -> {
                        if (element.getId().contains("_high_")) {
                            return Priority.HIGH;
                        } else if (element.getId().contains("_middle_")) {
                            return Priority.MIDDLE;
                        } else if (element.getId().contains("_low_")) {
                            return Priority.LOW;
                        } else if (element.getId().contains("_veryLow_")) {
                            return Priority.VERY_LOW;
                        } else {
                            throw new IllegalArgumentException("Incorrect parrent pane");
                        }
                    })
                    .findFirst();

            return chosen.get();
        } else {
            throw new IllegalArgumentException("Incorrect parent pane");
        }
    }

    private int sendToService(UserStory userStory) {
        return globalModel.getUserStories().size();
    }
}
