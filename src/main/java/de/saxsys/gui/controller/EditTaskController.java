package de.saxsys.gui.controller;

import de.saxsys.model.Priority;
import de.saxsys.model.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class EditTaskController implements EventHandler<ActionEvent> {
    Task taskModel;

    public EditTaskController(Task taskModel) {
        this.taskModel = taskModel;
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getEventType().equals(ActionEvent.ACTION) && event.getSource() instanceof Button && ((Button) event.getSource()).getId().equals("edittask_button")) {
            Button source = (Button) event.getSource();
            if (source.getParent() instanceof GridPane) {
                GridPane mainView = (GridPane) source.getParent();

                taskModel.setTitle(getTitleFromInput(mainView));
                taskModel.setDescription(getDescriptionFromInput(mainView));
                taskModel.setPriority(getPriorityFromInput(mainView));
                taskModel.setInCharge(getInChargeFromInput(mainView));

                sendUpdatedModelToServer();
            }
        }
    }

    private String getTitleFromInput(GridPane mainView) {
        if (mainView.lookup("#edittask_title_field") instanceof TextField) {
            TextField titleField = (TextField) mainView.lookup("#edittask_title_field");
            return titleField.getText();
        } else {
            throw new IllegalArgumentException("Incorrect parent pane");
        }
    }

    private String getDescriptionFromInput(GridPane mainView) {
        if (mainView.lookup("#edittask_description_field") instanceof TextArea) {
            TextArea descriptionField = (TextArea) mainView.lookup("#edittask_description_field");
            return descriptionField.getText();
        } else {
            throw new IllegalArgumentException("Incorrect parent pane");
        }
    }

    private Priority getPriorityFromInput(GridPane mainView) {
        if (mainView.lookup("#edittask_priority_view") instanceof VBox) {
            VBox priorityRadioView = (VBox) mainView.lookup("#edittask_priority_view");

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

    private String getInChargeFromInput(GridPane mainView) {
        if (mainView.lookup("#edittask_inCharge_field") instanceof TextField) {
            TextField titleField = (TextField) mainView.lookup("#edittask_inCharge_field");
            return titleField.getText();
        } else {
            throw new IllegalArgumentException("Incorrect parent pane");
        }
    }

    private void sendUpdatedModelToServer() {
        //send to server
    }
}
